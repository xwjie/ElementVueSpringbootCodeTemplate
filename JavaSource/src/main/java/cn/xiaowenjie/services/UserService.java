package cn.xiaowenjie.services;

import cn.xiaowenjie.springconfigs.ShiroConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xiaowenjie.common.rbac.User;
import cn.xiaowenjie.common.daos.UserDao;

/**
 * 用户相关处理类，目前是模拟数据
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
@Service
public class UserService {

	@Autowired
	UserDao userDao;

	public User findUser(String username) {
		return userDao.findByName(username);
	}

	/**
	 * FIXME
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password) {
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		subject.login(token);

		User user = (User) subject.getPrincipal();

		return user;
		//return findUser(username);
	}


	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}
}
