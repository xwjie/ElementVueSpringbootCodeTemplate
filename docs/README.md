# 介绍

使用Vue+VueX+ElementUI+SpringBoot的代码框架。详细讲解：[程序员你为什么这么累？](https://zhuanlan.zhihu.com/p/28705206)


![](./main.png)

## 特色

* 前后台一站式代码模板

## 技术点

### 后台
* SpringBoot
* Spring Data JPA
* Spring Cache
* JMS

### 前台
* VUE + VUEX + ElementUI

## 使用方法

### 数据库
使用 `h2` 内置数据库演示 `spring data jpa` 功能。无需任何配置。

使用了redis做cache，可以启动redis，不启动也暂时没有影响。

### 测试数据

先执行 `junit` 方法 `SpringbootCodeTemplateApplicationTests.addTestData` 增加测试数据。

### 后台

导入目录`JavaSource`到IDE，直接启动springboot工程即可。默认8080端口

### 前台

使用 `VSCode` 打开 `WebProject` , 运行 `npm run dev` ，会自动打开 [http://localhost:9090/](http://localhost:9090/) 。


如需要登陆，默认的内置账号密码为 `xwjie/xwjie` 。

## 辅助操作界面 

* swagger [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* H2维护界面 [http://127.0.0.1:8080/h2-console/](http://127.0.0.1:8080/h2-console/)
* 自己编写的Cache界面  [http://localhost:8080/cache.html](http://localhost:8080/cache.html)
