package cn.xiaowenjie.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.common.beans.PageReq;
import cn.xiaowenjie.common.beans.PageResp;
import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.services.ConfigService;

/**
 * 配置相关的controller，支持跨域
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
@RequestMapping("/config")
@RestController
@CrossOrigin
public class ConfigController {

	@Autowired
	ConfigService configService;

	@GetMapping("/all")
	public ResultBean<Collection<Config>> getAll() {
		return new ResultBean<Collection<Config>>(configService.getAll());
	}

	@GetMapping(value = "/list")
	public ResultBean<PageResp<Config>> list(PageReq param) {
		return new ResultBean<>(configService.listPage(param.toPageable(), param.getKeyword()));
	}

	/**
	 * 新增配置
	 * 
	 * FIXME 同时支持json格式和表单格式
	 * 
	 * @param config
	 * @return
	 */
	@PostMapping("/add")
	public ResultBean<Long> add(@RequestBody Config config) {
		System.out.println(configService.getClass());
		return new ResultBean<Long>(configService.add(config));
	}

	@PostMapping("/delete")
	public ResultBean<Boolean> delete(long id) {
		return new ResultBean<Boolean>(configService.delete(id));
	}
}
