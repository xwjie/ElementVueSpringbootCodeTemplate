# Spring Security 使用

## 增加依赖

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## response 多了三个头

```
X-Content-Type-Options:nosniff
X-Frame-Options:DENY
X-XSS-Protection:1; mode=block
```

## 指定认证方式

使用basic认证和普通的页面输入认证，只能选一种，否则返回401的时候还会重定向到登录页面。

* basis 认证

```java
.httpBasic().and()
```

* 页面认证

```java
.formLogin().loginPage("/login").permitAll().and()
```

完整：

```java
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

## 修改 X-Frame-Options

spring security 默认的 `X-Frame-Options` 是 `deny` ，修改为 `SAMEORIGIN` ，否则连h2都无法嵌入显示了。如下：

```java
http.headers().frameOptions().sameOrigin();
```
