package cn.xiaowenjie.beans;

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
