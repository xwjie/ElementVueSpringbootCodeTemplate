package cn.xiaowenjie.jms;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JMSMailComsumer {

	@Autowired
	private JavaMailSender mailSender;

	@JmsListener(destination = JMSType.SEND_MAIL)
	public void sendMail(@Payload Object message) {
		log.info("send mail: {}", message);

		if (message instanceof MimeMessage) {
			mailSender.send((MimeMessage) message);
		} else {
			mailSender.send((SimpleMailMessage) message);
		}
	}
}
