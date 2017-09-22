package cn.xiaowenjie.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.common.utils.UserUtil;
import cn.xiaowenjie.config.ServerCfg;

@RestController
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@PostMapping("/login")
	public ResultBean<String> login(HttpSession session, String username) {
		logger.info("login user:" + username);

		// TODO 只是模拟登陆
		session.setAttribute(UserUtil.KEY_USER, username);

		return new ResultBean<String>(username);
	}

	@Autowired
	ServerCfg cfg;

	@GetMapping(value = "/configTest")
	public ResultBean<ServerCfg> configTest() {
		return new ResultBean<ServerCfg>(cfg);
	}
}
