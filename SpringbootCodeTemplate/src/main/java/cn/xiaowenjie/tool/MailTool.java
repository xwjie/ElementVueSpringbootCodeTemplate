package cn.xiaowenjie.tool;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import cn.xiaowenjie.jms.JMSTool;
import cn.xiaowenjie.jms.JMSType;
import lombok.SneakyThrows;

@Component
public class MailTool {

	@Autowired
	JMSTool jmsTool;

	@Value("${spring.mail.from}")
	private String from;

	@Autowired
	private JavaMailSender mailSender;

	public void send(String subjct, String text, String... to) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subjct);
		message.setText(text);

		jmsTool.send(JMSType.SEND_MAIL, message);
	}

	@SneakyThrows
	public void sendHtml(String subjct, String text, String... to) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

		messageHelper.setFrom(from);
		messageHelper.setTo(to);
		messageHelper.setSubject(subjct);

		// html格式，第二个参数为true
		messageHelper.setText(text, true);

		// 附件
		// messageHelper.addAttachment

		jmsTool.send(JMSType.SEND_MAIL, mimeMessage);
	}
}