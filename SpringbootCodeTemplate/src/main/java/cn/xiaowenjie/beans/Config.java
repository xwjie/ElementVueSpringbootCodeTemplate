package cn.xiaowenjie.beans;

import java.io.Serializable;

import lombok.Data;

@Data
public class Config implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name, description, value;

	private long id;
	
	/**
	 * 创建者
	 */
	private long creator;
}
