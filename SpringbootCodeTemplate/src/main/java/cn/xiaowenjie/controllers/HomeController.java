package cn.xiaowenjie.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xiaowenjie.beans.User;
import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.common.consts.Roles;
import cn.xiaowenjie.common.utils.UserUtil;
import cn.xiaowenjie.config.ServerCfg;

@RestController
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@PostMapping("/login")
	public ResultBean<User> login(HttpSession session, String username) {
		logger.info("login user:" + username);

		// TODO 只是模拟登陆
		User user = new User(100L, username, "晓风轻", Roles.ADMIN);
		session.setAttribute(UserUtil.KEY_USER, user);

		return new ResultBean<User>(user);
	}

	@Autowired
	ServerCfg cfg;

	@GetMapping(value = "/configTest")
	public ResultBean<ServerCfg> configTest() {
		return new ResultBean<ServerCfg>(cfg);
	}
}
