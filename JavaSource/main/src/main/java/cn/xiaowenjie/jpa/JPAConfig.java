package cn.xiaowenjie.jpa;

import cn.xiaowenjie.common.rbac.User;
import cn.xiaowenjie.common.utils.UserUtil;
import cn.xiaowenjie.jpa.JPAThreadLocal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
// 不指定bean也可以 @EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JPAConfig {
	@Bean
	public AuditorAware<User> auditorAware() {
		return new AuditorAware<User>() {

			@Override
			public User getCurrentAuditor() {
				//System.out.println("\n\nJPAConfig.auditorAware().new AuditorAware() {...}.getCurrentAuditor()");

				// 后台任务，不需要登录
				// TODO 后台创建的生活，可能就会为空
				if (JPAThreadLocal.background()){
					return null;
				}
				else {
					return UserUtil.getUser();
				}
			}
		};
	}
}