package cn.xiaowenjie.beans;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class Config extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name, description, value;

	/**
	 * 创建者
	 */
	private long creator;
}
