package cn.xiaowenjie.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.common.exceptions.CheckException;
import cn.xiaowenjie.common.utils.UserUtil;

/**
 * app相关的controller，支持跨域
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
@RequestMapping("/app")
@RestController
@CrossOrigin
public class AppController {
	private static final Logger logger = LoggerFactory.getLogger(AppController.class);

	@PostMapping(value="/login")
	public ResultBean<String> login(HttpSession session, String username, String password) {
		logger.info("login user:" + username);

		// 测试代码
		if (!"xwjie".equalsIgnoreCase(password)) {
			throw new CheckException("密码输入xwjie");
		}

		// TODO 只是模拟登陆
		session.setAttribute(UserUtil.KEY_USER, username);

		return new ResultBean<String>(username);
	}
}
