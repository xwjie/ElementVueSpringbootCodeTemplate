# 加入依赖

```
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

```

# 定义实体类

```Java
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Config implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name, description, value;

	@Id
	@GeneratedValue
	private long id;

	/**
	 * 创建者
	 */
	private long creator;
}
```

# 定义Dao（ Repository ）

可以使用 `PagingAndSortingRepository` 或者 `CrudRepository`。

增加了一个更加名称查找的方法。

```
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.xiaowenjie.beans.Config;

public interface ConfigDao extends PagingAndSortingRepository<Config, Long> {
	Config findByName(String name);
}
```

# 配置数据库

配置使用h2数据库，内存型的时候url为 `jdbc:h2:mem:mydb` 。 文件类型为：`jdbc:h2:file:~/mydb.h2`

```
spring:
  profiles:
    active: dev
  redis:
    host: localhost
    port: 6379
  datasource:
    url: jdbc:h2:file:~/mydb.h2
    #url: jdbc:h2:mem:mydb
    username: sa
    password: sa
    driverClassName: org.h2.Driver
  jpa:
    database: h2
    show-sql: true
    hibernate:
      ddl-auto: update
  h2:
    console:
      enabled: true
      path: /h2-console
      settings:
        web-allow-others: true
        trace: true
```

# 引入guava， 把查询结果转为list

使用jpa查询结果中，返回的为 iterable，需要转换为list返回给前台。

```
<dependency>
	<groupId>com.google.guava</groupId>
	<artifactId>guava</artifactId>
	<version>16.0.1</version>
</dependency>
```

```Java
import com.google.common.collect.Lists;

public Collection<Config> getAll() {
	// 校验通过后打印重要的日志
	logger.info("getAll start ...");

	List<Config> data = Lists.newArrayList(dao.findAll());

	logger.info("getAll end, data size:" + data.size());

	return data;
}
```

# 使用h2的web console

访问 http://127.0.0.1:8080/h2-console/

![](/pictures/h2-1.png)

可以选择中文界面，赞，填写好url，链接进去：


![](/pictures/h2-2.png)

使用文件类型后，会在用户的目录下创建数据库文件，trace文件里面是文本，为数据库错误的一些日志堆栈。


![](/pictures/h2-3.png)

|注意：上线稳定的时候注意把控制台关掉。