package cn.xiaowenjie.SpringbootCodeTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.xiaowenjie.SpringbootCodeTemplateApplication;
import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.services.ConfigService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootCodeTemplateApplication.class)
public class SpringbootCodeTemplateApplicationTests {

	@Autowired
	ConfigService configService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void addTestData() {
		for (int i = 0; i < 110; i++) {
			Config config = new Config();

			config.setName("测试数据：" + i);
			config.setValue("https://github.com/xwjie");
			config.setDescription("晓风轻：" + i);
			configService.add(config);
		}
	}
}
