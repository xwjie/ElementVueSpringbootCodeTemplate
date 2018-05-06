package cn.xiaowenjie.springconfigs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import cn.xiaowenjie.beans.User;
import cn.xiaowenjie.common.consts.Roles;
import cn.xiaowenjie.common.utils.UserUtil;

@Configuration
@EnableJpaAuditing
// 不指定bean也可以 @EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JPAConfig {
	@Bean
	public AuditorAware<User> auditorAware() {
		return new AuditorAware<User>() {

			@Override
			public User getCurrentAuditor() {
				System.out.println("\n\nJPAConfig.auditorAware().new AuditorAware() {...}.getCurrentAuditor()");

				return UserUtil.getUser();
			}
		};
	}
}