package cn.xiaowenjie.beans;

import javax.persistence.Entity;

import cn.xiaowenjie.common.consts.Roles;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 * 
 * @author 肖文杰 https://github.com/xwjie/
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	private String nick;

	/**
	 * 角色
	 */
	private String role = Roles.USER;

}
