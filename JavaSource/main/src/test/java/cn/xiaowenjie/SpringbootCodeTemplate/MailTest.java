package cn.xiaowenjie.SpringbootCodeTemplate;

import cn.xiaowenjie.SpringbootCodeTemplateApplication;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.xiaowenjie.tool.MailTool;

/**
 * 发送邮件测试（异步）
 * 
 * @author 晓风轻
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootCodeTemplateApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MailTest {

  @Autowired
  MailTool mailTool;

  @Test
  public void test1_sendmai() {
    mailTool.send("测试发送标题", "这是正文\n没有html", "1304471323@qq.com");
  }

  //FIXME @Test
  public void test2_sendhtmlmai() {
    mailTool.sendHtml("测试发送标题html",
        "<html><body><h1>这是正文</h1><br/><b>html</b></body></html>",
        "1304471323@qq.com");
  }
}
