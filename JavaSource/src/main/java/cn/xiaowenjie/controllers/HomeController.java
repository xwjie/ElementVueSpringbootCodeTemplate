package cn.xiaowenjie.controllers;

import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.config.ServerCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	ServerCfg cfg;

	@GetMapping(value = "/configTest")
	public ResultBean<ServerCfg> configTest() {
		return new ResultBean<ServerCfg>(cfg);
	}
}
