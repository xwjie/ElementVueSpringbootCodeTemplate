# 增加基类，实现了自动生成 createTime和updateTime

# 增加基类

把id和创建修改时间放到基类里面。需要使用 `@MappedSuperclass` 注解。

使用 `@CreationTimestamp`  和 `@UpdateTimestamp` 自动生成对应的时间。

```Java
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
 * @author 肖文杰 https://github.com/xwjie/
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