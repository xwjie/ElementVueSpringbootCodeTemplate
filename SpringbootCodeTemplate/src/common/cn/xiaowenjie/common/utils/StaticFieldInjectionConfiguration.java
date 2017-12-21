package cn.xiaowenjie.common.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

import cn.xiaowenjie.common.filters.UserFilter;
import cn.xiaowenjie.services.UserService;

/**
 * 工具类的注入
 * 
 * @author 肖文杰
 * 
 */
@Configuration
public class StaticFieldInjectionConfiguration {

	@Autowired
	MessageSource resources;

	@Autowired
	UserService userService;

	@PostConstruct
	private void init() {
		System.out.println("\n\n-----StaticFieldInjectionConfiguration----\n\n");
		CheckUtil.setResources(resources);
		UserFilter.setUserService(userService);
	}
}