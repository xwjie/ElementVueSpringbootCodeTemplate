package cn.xiaowenjie.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xiaowenjie.beans.User;
import cn.xiaowenjie.daos.UserDao;

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

}
