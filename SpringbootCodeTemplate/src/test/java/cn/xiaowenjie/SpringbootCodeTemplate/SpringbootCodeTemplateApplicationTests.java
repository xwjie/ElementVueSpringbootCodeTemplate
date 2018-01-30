package cn.xiaowenjie.SpringbootCodeTemplate;

import java.util.Collection;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.xiaowenjie.SpringbootCodeTemplateApplication;
import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.beans.User;
import cn.xiaowenjie.common.consts.Roles;
import cn.xiaowenjie.daos.UserDao;
import cn.xiaowenjie.services.ConfigService;
import cn.xiaowenjie.tool.MailTool;

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

		User user = new User();

		user.setId(1000L);
		user.setName("xwjie");
		user.setNick("晓风轻");
		user.setRole(Roles.ADMIN);

		userDao.save(user);

		for (int i = 1; i <= 10; i++) {
			User u = new User();

			user.setId(i);
			user.setName("user" + i);
			user.setNick("测试用户" + i);
			user.setRole(Roles.USER);

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

	@Autowired
	MailTool mailTool;

	@Test
	public void test4_sendmai() {
		mailTool.send("测试发送标题", "这是正文\n没有html", "1304471323@qq.com");
	}
	
	@Test
	public void test5_sendhtmlmai() {
		mailTool.sendHtml("测试发送标题html", "<html><body><h1>这是正文</h1><br/><b>html</b></body></html>", "1304471323@qq.com");
	}
}
