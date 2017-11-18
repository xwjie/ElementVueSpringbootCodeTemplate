# ElementVueSpringbootCodeTemplate
使用Vue+VueX+ElementUI+SpringBoot的代码框架

# :sparkles: 使用h2数据库（2017.11.18）

删除自己写的ConcurrentSkipListMap实现，改用spring-data-jpa实现，更加有实际意义。使用的为h2数据库（文件模式）

[使用h2数据库配置](spring-jpa-data-use-h2-database.md)

# ElementUI升级到2.0.2 (2017.11.02)

先升级vue到2.5.2，在升级elementui

```
npm update fsevents
npm update vue
npm update vue-template-compiler

npm uninstall element-ui
npm install element-ui@2.0.2 -S
```

修改elementui的css路径：

```
import 'element-ui/lib/theme-default/index.css'
```

改为

```
import 'element-ui/lib/theme-chalk/index.css'
```

done.


# 增加redis依赖

```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

# 增加redis配置

`application.yml` 上增加：

```
spring:
  redis:
    host: localhost
    port: 6379
```

# 配置redis

继承CachingConfigurerSupport，增加 `@EnableCaching` 注解，需要重写 `keyGenerator` 方法。

```Java
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport
```

在类里面配置 `RestTemplate` ，需要配置key和value的序列化类。

key序列化使用`StringRedisSerializer`, 不配置的话key前面会出现乱码。

value序列化使用 `GenericJackson2JsonRedisSerializer` ，保存为Json格式。该类目前反序列化还有一些bug，只能反序列化实现了Serialize的类。


```Java
@Bean
public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
  RedisTemplate template = new RedisTemplate();

  template.setConnectionFactory(factory);

  RedisSerializer keySerializer = new StringRedisSerializer();

  // 设置key序列化类，否则key前面会多了一些乱码
  template.setKeySerializer(keySerializer);
  template.setHashKeySerializer(keySerializer);

  // FIXME 有些版本有bug，没有序列化的只能序列化，但无法反序列化
  GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

  // 设置内容序列化类
  template.setValueSerializer(jsonSerializer);

  template.afterPropertiesSet();

  return template;
}
```


配置 CacheManager，包括指定缓存和默认缓存的超时时间的配置。

```Java
@Bean
public CacheManager cacheManager(RedisTemplate redisTemplate) {
  RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

  Map<String, Long> expires = new HashMap<>();

  expires.put(CacheNames.CONFIG, 60L);

  // 设置超时
  cacheManager.setExpires(expires);

  // 没有设置的缓存默认过期时间
  cacheManager.setDefaultExpiration(60 * 60);

  return cacheManager;
}
```


重写 `keyGenerator`，可以支持使用@Cacheable不指定Key。

```Java
@Override
public KeyGenerator keyGenerator() {
  return new KeyGenerator() {
    @Override
    public Object generate(Object target, Method method, Object... params) {
      StringBuilder sb = new StringBuilder();
      
      sb.append(target.getClass().getSimpleName());
      sb.append('.').append(method.getName());

      // FIXME 参数太长的时候请指定key属性，否则key太长
      for (Object obj : params) {
        if (obj != null) {
          sb.append(obj.toString());
        }
      }

      return sb.toString();
    }
  };
}
```

# 使用缓存

保存的时候使用 `@Cacheable`，清空使用 `@CacheEvict` ，更新的时候使用 `@CachePut` 。

反序列化有bug，没有实现 `Serializable` 的只能序列化，无法反序列化。可能后续版本会解决该问题。

所以把下面的查询代码修改一下，用 实现了 `Serializable` 的 `ArrayList` 包装返回。

```Java
@Cacheable("config")
@Override
public Collection<Config> getAll() {
  System.out.println("\n----------GetAll----------\n");
  return new ArrayList<>(configs.values());
}

@CacheEvict(value = CacheNames.CONFIG, allEntries = true)
@Override
public long add(Config config) {

}

/**
 * 删除配置项
 */
@CacheEvict(value = CacheNames.CONFIG, allEntries = true)
@Override
public boolean delete(long id) {
  
}
```

# 定时清空缓存

也可以定时清空cache，使用 `@EnableScheduling` 和 `@Scheduled` 注解。

```Java
@Component
@EnableScheduling
public class ClearCacheTask {

  /**
   * 定时清空缓存
   */
  @Scheduled(fixedRate = 60 * 1000L)
  @CacheEvict(value = { CacheNames.CONFIG }, allEntries = true)
  public void clearCaches() {
    System.out.println("\n------------ clear caches ------------\n");
  }
}
```

# redis 怎么样保存cache

增加2条数据，一个是类型为 `zset` 的 `缓存名~keys` , 里面存放了该缓存所有的key， 一个是对应的key，值为序列化后的json。

`zset` 是带权重的有序集合，可以使用 `zrange config~keys -1 1 withscores` 查看元素，新加入的都是 0.0 。使用 ` zcount config~keys -1 1` 查看个数。

可以使用 `ttl` 命令查看超时时间，单位为秒。

![redis console](/pictures/redisconsole.png) 


# 安装redis

https://github.com/MicrosoftArchive/redis/releases 下载最新版本，3.2

启动
```
redis-server.exe redis.windows.conf
```

也可以使用 [Redis Client](https://github.com/caoxinyu/RedisClient) 查看。

![redis client](/pictures/redisclient.png) 

# redis 比较重要命令
* keys * / keys cn* 查看数据
* type keyname 查看数据类型
* dbsize 查看记录数
* flushdb 删除【当前数据库】所有记录
* flushall 删除所有数据库里面的所有数据，注意不是当前数据库，而是所有数据库。
* expire，ttl 设置和查看超时时间
* select 选择0-15号数据库