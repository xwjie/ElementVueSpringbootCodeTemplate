package cn.xiaowenjie.common.utils;

import javax.annotation.PostConstruct;

import cn.xiaowenjie.daos.ConfigDao;
import cn.xiaowenjie.tool.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	ConfigDao configDao;

	@PostConstruct
	private void init() {
		System.out.println("\n\n-----StaticFieldInjectionConfiguration----\n\n");
		CheckUtil.setResources(resources);
		ConfigUtil.setConfigDao(configDao);
	}
}