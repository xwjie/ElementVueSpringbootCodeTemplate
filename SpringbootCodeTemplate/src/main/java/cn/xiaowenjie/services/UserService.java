package cn.xiaowenjie.services;

import static cn.xiaowenjie.common.utils.CheckUtil.*;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.beans.User;
import cn.xiaowenjie.common.exceptions.CheckException;
import cn.xiaowenjie.daos.ConfigDao;


/**
 * 用户相关处理类，目前是模拟数据
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public User login(String username, String password) {
		// 测试代码
		if ("xwjie".equals(username) && "xwjie".equals(password)) {
			return new User(1000L, username, "晓风轻");
		}

		throw new CheckException("请用户名和密码都输入xwjie");
	}

}
