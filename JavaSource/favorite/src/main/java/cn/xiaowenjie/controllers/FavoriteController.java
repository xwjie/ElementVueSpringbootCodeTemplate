package cn.xiaowenjie.controllers;

import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.beans.Favorite;
import cn.xiaowenjie.common.beans.PageReq;
import cn.xiaowenjie.common.beans.PageResp;
import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.services.ConfigService;
import cn.xiaowenjie.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 配置相关的controller，支持跨域
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
@RequestMapping("/favorite")
@RestController
@CrossOrigin
public class FavoriteController {

	@Autowired
	FavoriteService favoriteService;

	@GetMapping("/all")
	public ResultBean<Collection<Favorite>> getAll(@RequestParam int type) {
		return new ResultBean<Collection<Favorite>>(favoriteService.getAll(type));
	}

//	@GetMapping(value = "/list")
//	public ResultBean<PageResp<Config>> list(PageReq param) {
//		return new ResultBean<>(configService.listPage(param.toPageable(), param.getKeyword()));
//	}
//
//	/**
//	 * 新增配置
//	 *
//	 * FIXME 同时支持json格式和表单格式
//	 *
//	 * @param config
//	 * @return
//	 */
//	@PostMapping("/add")
//	public ResultBean<Long> add(@RequestBody Config config) {
//		System.out.println(configService.getClass());
//		return new ResultBean<Long>(configService.add(config));
//	}
//
//	@PostMapping("/delete")
//	public ResultBean<Boolean> delete(long id) {
//		return new ResultBean<Boolean>(configService.delete(id));
//	}
}
