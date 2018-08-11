package cn.xiaowenjie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan
@EnableSwagger2
public class SpringbootCodeTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCodeTemplateApplication.class, args);
	}

	/**
	 *  国际化配置
	 * @return
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("i18n/messages"); // , "i18n/errormsg"
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
