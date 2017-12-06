package cn.xiaowenjie.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xiaowenjie.beans.TreeNode;
import cn.xiaowenjie.beans.User;
import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.common.consts.Roles;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@GetMapping("/search")
	public ResultBean<List<User>> search(String keyword) {
		System.out.println("UserController.search()" + keyword);

		List<User> nodes = Arrays.asList(new User(999L, "dddddddd", "大大大", Roles.ADMIN),
				new User(999L, "dddddd2", "大大大4", Roles.ADMIN), new User(999L, "ddddd4d", "大大33", Roles.ADMIN));

		return new ResultBean<>(nodes);
	}

}
