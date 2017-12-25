package cn.xiaowenjie.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

		User user = new User();

		user.setId(1000L);
		user.setName("xwjie");
		user.setNick("晓风轻");
		user.setRole(Roles.ADMIN);

		List<User> nodes = Arrays.asList(user);

		return new ResultBean<>(nodes);
	}

}
