package cn.xiaowenjie.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.xiaowenjie.common.rbac.User;
import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.common.consts.Roles;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	/**
	 * 测试数据
	 * 
	 * @param keyword
	 * @return
	 */
	@GetMapping("/search")
	public ResultBean<List<User>> search(@RequestParam String keyword) {
		System.out.println("UserController.search()" + keyword);

		List<User> nodes = Arrays.asList();

		return new ResultBean<>(nodes);
	}

}
