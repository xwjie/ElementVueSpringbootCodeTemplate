package cn.xiaowenjie.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

import cn.xiaowenjie.common.consts.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 * 
 * @author 肖文杰 https://github.com/xwjie/
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	private String name;

	private String nick;

	/**
	 * 角色
	 */
	private String role = Roles.USER;

}
