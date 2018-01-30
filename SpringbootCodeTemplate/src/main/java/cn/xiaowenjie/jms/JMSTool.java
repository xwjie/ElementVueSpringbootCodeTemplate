package cn.xiaowenjie.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JMSTool {

	@Autowired
	JmsTemplate jmsTemplate;

	public void send(String destination, Object message) {
		jmsTemplate.convertAndSend(destination, message);
	}
}
