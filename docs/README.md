# 介绍

使用Vue+VueX+ElementUI+SpringBoot的代码框架。相关文章讲解：[程序员你为什么这么累？](https://zhuanlan.zhihu.com/p/28705206)


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

使用了redis做cache，需启动redis，默认端口。

### 测试数据

`CreateTestData` 类会自动执行，创建测试数据。

### 后台

导入目录`JavaSource`到IDE，使用了[lombok](https://projectlombok.org/)，需要在IDE里面先安装插件。

然后需要把 `source\src\common` 导入为源代码目录即可。（Eclipse里面在该目录选择右键 "Build Path" -> "Use as Source Folder"）

直接启动springboot工程即可。默认8080端口

### 前台

使用 `VSCode` 打开 `WebProject` , 运行 `npm run dev` ，会自动打开 [http://localhost:9090/](http://localhost:9090/) 。


如需要登陆，默认的内置账号密码为 `xwjie/xwjie` 。

## 辅助操作界面 

* swagger [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

* H2维护界面 [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/)

1. JDBC URL： jdbc:h2:~/mydb.h2
2. 用户名：sa
3. 密码：sa

* 自己编写的Cache界面  [http://localhost:8080/cache.html](http://localhost:8080/cache.html)
