package cn.xiaowenjie.common.rbac;

import javax.persistence.*;

import cn.xiaowenjie.beans.BaseEntity;
import cn.xiaowenjie.common.consts.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户信息
 * 
 * @author 肖文杰 https://github.com/xwjie/
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(indexes = { @Index(name = "user_name_unique", columnList = "name", unique = true) })
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	private String nick;

	/**
	 *  加密的盐
	 */
	@JsonIgnore
	private String salt;

	/**
	 *  密码
     *  FIXME 注意任何时候不能返回到前台，必要时候安全相关字段放另外一个表
	 */
	@JsonIgnore
	private String password;

	/**
	 * 角色
	 */
	@ManyToMany(fetch = FetchType.EAGER )
	@JoinTable(name="link_user_role",
			joinColumns={ @JoinColumn(name="user_id",referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="role_id",referencedColumnName="id")})
	private List<Role> roles;

}
