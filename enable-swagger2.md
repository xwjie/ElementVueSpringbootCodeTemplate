# 增加swagger

在 `pom.xml` 里面增加以下2个依赖。

![](/pictures/swagger1.png)

# 开启swagger

增加 `@EnableSwagger2` 。

```Java
@EnableSwagger2
public class SpringbootCodeTemplateApplication{
  ...
}
```

访问 http://localhost:8080/swagger-ui.html ，默认把所有接口都列出来了，效果如图：

![](/pictures/swagger2.png)
