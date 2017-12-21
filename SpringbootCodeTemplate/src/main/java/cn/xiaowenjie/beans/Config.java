package cn.xiaowenjie.beans;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Config extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name, description, value;

	/**
	 * 创建者
	 */
	@CreatedBy
	@ManyToOne
	private User creator;
}
