package cn.xiaowenjie.controllers;

import cn.xiaowenjie.beans.Blog;
import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.common.beans.PageReq;
import cn.xiaowenjie.common.beans.PageResp;
import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * blog
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
@RequestMapping("/blog")
@RestController
@CrossOrigin
public class BlogController {

	@Autowired
	BlogService blogService;

	@GetMapping("/all")
	public ResultBean<Collection<Blog>> getAll() {
		return new ResultBean<Collection<Blog>>(blogService.getAll());
	}

	@GetMapping(value = "/list")
	public ResultBean<PageResp<Blog>> list(PageReq param) {
		return new ResultBean<>(blogService.listPage(param.toPageable(), param.getKeyword()));
	}

	/**
	 * 新增配置
	 * 
	 * @param blog
	 * @return
	 */
	@PostMapping("/add")
	public ResultBean<Long> add(@RequestBody @Valid Blog blog) {
		return new ResultBean<Long>(blogService.add(blog));
	}

	@PostMapping("/delete")
	public ResultBean<Boolean> delete(long id) {
		return new ResultBean<Boolean>(blogService.delete(id));
	}
}
