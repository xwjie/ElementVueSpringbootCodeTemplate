package cn.xiaowenjie.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import cn.xiaowenjie.jpa.JPAListener;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基类
 * 
 * @author 肖文杰 https://github.com/xwjie/
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(of = "id")
@EntityListeners(value = JPAListener.class)
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@CreationTimestamp
	private Date createTime;

	@UpdateTimestamp
	private Date updateTime;

}
