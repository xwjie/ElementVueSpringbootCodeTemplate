package cn.xiaowenjie.jms;

import javax.jms.Message;
import javax.jms.Session;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JMSMailComsumer {

	@Autowired
	private JavaMailSender mailSender;

	@JmsListener(destination = JMSType.SEND_MAIL)
	public void sendMail(@Payload Object obj, @Headers MessageHeaders headers, Message message, Session session) {
		log.info("recived mail: {}", obj);

		System.out.println("-------------------------");
		System.out.println("obj:" + obj);
		System.out.println("headers:" + headers);
		System.out.println("message:" + message);
		System.out.println("session:" + session);
		System.out.println("-------------------------");

		if (obj instanceof MimeMessage) {
			mailSender.send((MimeMessage) obj);
		} else {
			mailSender.send((SimpleMailMessage) obj);
		}
	}
}
