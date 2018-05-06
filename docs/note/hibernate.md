# hibernate 笔记

## 自动生成时间

增加基类，把id和创建修改时间放到基类里面，并把类修改为 `abstract` 。

需要使用 `@MappedSuperclass` 注解。

使用 `@CreationTimestamp`  和 `@UpdateTimestamp` 自动生成对应的时间。

```java
package cn.xiaowenjie.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

/**
 * 基类
 * 
 * @author 晓风轻 https://github.com/xwjie/
 */
@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	@Id
	@GeneratedValue
	private long id;

	@CreationTimestamp
	private Date createTime;

	@UpdateTimestamp
	private Date updateTime;

}

```

## 增加唯一主键

User表：

```java
@Table(indexes = { @Index(name = "user_name_unique", columnList = "name", unique = true) })
```