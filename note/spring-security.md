# 增加依赖

```XML
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

# response 多了三个头

```
X-Content-Type-Options:nosniff
X-Frame-Options:DENY
X-XSS-Protection:1; mode=block
```

# 指定认证方式

使用basic认证和普通的页面输入认证，只能选一种，否则返回401的时候还会重定向到登录页面。

* basis 认证

```
.httpBasic().and()
```

* 页面认证

```Java
.formLogin().loginPage("/login").permitAll().and()
```

完整：

```Java
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
}
```

