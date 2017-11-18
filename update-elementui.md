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


# 引入Redis

为什么要引入Redis？使用和配置见：[使用Redis Cache](use-redis-cache.md)