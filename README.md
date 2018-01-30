# ElementVueSpringbootCodeTemplate
使用Vue+VueX+ElementUI+SpringBoot的代码框架。详细讲解：[程序员你为什么这么累？](https://zhuanlan.zhihu.com/p/28705206)


![](pictures/main.png)

# 特色

* 前后台一站式代码模板

# 技术点

## 后台
* SpringBoot

## 前台
* VUE + VUEX + ElementUI

# 使用方法

使用h2内置数据库演示 `spring data jpa` 功能。无需任何配置。

使用了redis做cache，可以启动redis，不启动也暂时没有影响。

先执行 `junit` 方法 `SpringbootCodeTemplateApplicationTests.addTestData` 增加测试数据。然后 启动后台springboot工程，在用 `npm run dev` 启动前台web工程即可。

如需要登陆，默认的内置账号密码为 `xwjie/xwjie` 。

# 2018.1.30 集成JMS，发送MAIL组件

- 使用 `jms` 执行 `异步任务` ，当然你也可以用 `线程池` 来做异步操作，使用 `jms` 的好处是将来好扩展
- 集成发送mail功能
- User表增加唯一主键
```
@Table(indexes = { @Index(name = "user_name_unique", columnList = "name", unique = true) })
```

# 2017.12.14 增加基类，实现了自动生成 createTime和updateTime

[Hibernate-增加基类，实现了自动生成 createTime和updateTime](note/Hibernate-auto-add-createTime-updateTime.md)

`spring security` 默认的 `X-Frame-Options` 是 `deny` ，修改为 `SAMEORIGIN` ，否则连h2都无法嵌入显示了。如下：

```Java
http.headers().frameOptions().sameOrigin();
```

# 2017.12.02 封装VUE组件，增加spring security

[VUE分页组件封装](https://zhuanlan.zhihu.com/p/31638307) , 使用简单，前后台代码都封装好了，相当不错。

[spring security知识点（未完）](note/spring-security.md)


# 2017.11.24 增加swagger2

[增加swagger2](enable-swagger2.md)

http://localhost:8080/swagger-ui.html

# 2017.11.22 增加vue过滤器

[增加vue全局过滤器](vue-filter.md)

# 2017.11.18 :sparkles: 使用h2数据库

删除自己写的ConcurrentSkipListMap实现，改用spring-data-jpa实现，更加有实际意义。使用的为h2数据库（文件模式）

[使用h2数据库配置](spring-jpa-data-use-h2-database.md)

# 2017.11.02 ElementUI升级到2.0.2

[ElementUI升级](update-elementui.md)

# 引入Redis

为什么要引入Redis？使用和配置见：[使用Redis Cache](use-redis-cache.md)