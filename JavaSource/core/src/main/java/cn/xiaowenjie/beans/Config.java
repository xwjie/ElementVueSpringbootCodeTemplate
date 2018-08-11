package cn.xiaowenjie.beans;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

import cn.xiaowenjie.common.rbac.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
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
