package cn.xiaowenjie.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * 发送jms消息
 * 
 * @author 晓风轻 https://github.com/xwjie/
 */
@Component
public class JMSTool {

	@Autowired
	JmsTemplate jmsTemplate;

	public void sendMessage(String destination, Object message) {
		jmsTemplate.convertAndSend(destination, message);
	}
}
