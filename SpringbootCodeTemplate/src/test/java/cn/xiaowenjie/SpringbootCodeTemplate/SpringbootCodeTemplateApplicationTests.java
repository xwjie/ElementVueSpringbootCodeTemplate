package cn.xiaowenjie.SpringbootCodeTemplate;

import java.util.Collection;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import cn.xiaowenjie.SpringbootCodeTemplateApplication;
import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.beans.User;
import cn.xiaowenjie.common.consts.Roles;
import cn.xiaowenjie.daos.UserDao;
import cn.xiaowenjie.services.ConfigService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootCodeTemplateApplication.class)
// TODO: junit测试用例按照顺序执行
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringbootCodeTemplateApplicationTests {

	@Autowired
	ConfigService configService;

	@Autowired
	UserDao userDao;


	@Test
	public void test1_addUser() {
		System.out.println("\n\n---addUser---\n\n");
		User user = new User(999, "xwjie", "晓风轻", Roles.ADMIN);
		userDao.save(user);

		for (int i = 1; i <= 10; i++) {
			User u = new User(i, "user" + i, "测试用户" + i, Roles.USER);
			userDao.save(u);
		}
	}

	@Test
	public void test2_addTestData() {
		System.out.println("\n\n---addTestData---\n\n");

		for (int i = 1; i <= 10; i++) {
			User user = userDao.findOne((long) i);
			for (int j = 0; j < 6; j++) {
				Config config = new Config();

				config.setName("测试数据：" + i + ":" + j);
				config.setValue("https://github.com/xwjie");
				config.setDescription("晓风轻：" + i + ":" + j);

				config.setCreator(user);

				configService.add(config);
			}
		}
	}

	@Test
	public void test3_getConfig() {
		System.out.println("\n\n---testGetConfig---\n\n");

		Collection<Config> all = configService.getAll();

		for (Config config : all) {
			System.out.println(config.getCreator());
		}
	}
}
