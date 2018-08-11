package cn.xiaowenjie.springconfigs;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JMSConfig {

	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message
		// converter
		configurer.configure(factory, connectionFactory);

		// You could still override some of Boot's default if necessary.

		// 设置连接数
		factory.setConcurrency("3-10");
		// 重连间隔时间
		factory.setRecoveryInterval(1000L);

		return factory;
	}

	/**
	 * 使用内置的activeMQ
	 */
	private static final String JMS_BROKER_URL = "vm://embedded?broker.persistent=false,useShutdownHook=false";

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(JMS_BROKER_URL);
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");

		return converter;
	}
}
