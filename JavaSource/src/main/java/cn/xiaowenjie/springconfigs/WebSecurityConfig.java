package cn.xiaowenjie.springconfigs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 先放开
		// http.authorizeRequests().anyRequest().permitAll();

		http.authorizeRequests()
				.antMatchers("/", "/home", "/resources/**", "/favicon.ico").permitAll()
				.anyRequest().authenticated().and()
				// basic返回401 ，【不能】配置loginpage
				.httpBasic().and()
				// 不是http认证的时候，返回302，需要配置loginPage链接
				// .formLogin()
				// .loginPage("/login")
				// .permitAll()
				// .and()
				.logout().permitAll();

		//这句话要放后面，否则被冲掉了无效
		http.csrf().disable();
		
		// 否则连h2都无法嵌入显示了
		http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// ajax 跨域预检命令不能返回401，否则浏览器就报错了。
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
		super.configure(web);
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("xwjie").password("xwjie").roles("USER");
	}

	// @Autowired
	// public initialize(AuthenticationManagerBuilder builder, DataSource dataSource) {
	// builder.jdbcAuthentication().dataSource(dataSource).withUser("dave")
	// .password("secret").roles("USER");
	// }
}
