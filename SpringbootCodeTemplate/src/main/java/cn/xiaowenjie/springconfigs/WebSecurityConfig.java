package cn.xiaowenjie.springconfigs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// 先放开
    	http.authorizeRequests().anyRequest().permitAll();
    	
//        http
//            .authorizeRequests()
//                .antMatchers("/", "/home","/resources/**", "/favicon.ico").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                // basic返回401 ，【不能】配置loginpage
//                .httpBasic()
//                .and()
//              // 不是http认证的时候，返回302，需要配置loginPage链接
////            .formLogin()
////                .loginPage("/login")
////                .permitAll()
////                .and()
//            .logout()
//                .permitAll();
        
        //http.csrf().disable();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("xwjie").password("xwjie").roles("USER");
    }

//    @Autowired
//    public initialize(AuthenticationManagerBuilder builder, DataSource dataSource) {
//      builder.jdbcAuthentication().dataSource(dataSource).withUser("dave")
//        .password("secret").roles("USER");
//    }
}
