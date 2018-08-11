package cn.xiaowenjie.springconfigs;

import cn.xiaowenjie.tool.PasswordUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class ShiroConfig {


    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") MyAuthorizingRealm authRealm,
                                           @Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager) {
        log.info("securityManager()");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 设置realm.
        securityManager.setRealm(authRealm);

        // 设置rememberMe管理器
        securityManager.setRememberMeManager(cookieRememberMeManager);

        return securityManager;
    }

    /**
     * realm
     *
     * @return
     */
    @Bean(name = "authRealm")
    public MyAuthorizingRealm myAuthRealm(
            @Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher
            // CacheManager cacheManager
    ) {
        log.info("myShiroRealm()");
        MyAuthorizingRealm myAuthorizingRealm = new MyAuthorizingRealm();

        // 设置密码凭证匹配器
        myAuthorizingRealm.setCredentialsMatcher(matcher);

        // 设置缓存管理器
        // myAuthorizingRealm.setCacheManager(cacheManager);

        return myAuthorizingRealm;
    }

/**
 * 缓存管理器
 * @return
 */
//    @Bean(value="ehCacheManager")
//    public EhCacheManager ehCacheManager(
//            @Qualifier("ehCacheManagerFactoryBean") EhCacheManagerFactoryBean bean) {
//        log.info("ehCacheManager()");
//        EhCacheManager cacheManager = new EhCacheManager();
//        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
//        return cacheManager;
//    }

    /**
     * cookie对象;
     *
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        // 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");

        // <!-- 记住我cookie生效时间30天（259200） ,单位秒;-->
        simpleCookie.setMaxAge(259200);

        return simpleCookie;
    }

    /**
     * 记住我管理器 cookie管理对象;
     *
     * @return
     */
    @Bean(name = "cookieRememberMeManager")
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();

        cookieRememberMeManager.setCookie(rememberMeCookie());

        return cookieRememberMeManager;
    }

    /**
     * 密码匹配凭证管理器
     *
     * @return
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {

        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName(PasswordUtil.ALGORITHM_NAME);
        hashedCredentialsMatcher.setHashIterations(PasswordUtil.HASH_ITERATIONS);// 散列的次数，比如散列两次，相当于

        return hashedCredentialsMatcher;
    }
//
//    @Bean("lifecycleBeanPostProcessor")
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean
//    @DependsOn("lifecycleBeanPostProcessor")
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
//        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
//
//        // 强制使用cglib
//        creator.setProxyTargetClass(true);
//
//        return  creator;
//    }

    /**
     * 开启shiro aop注解支持.
     *
     * 使用代理方式;所以需要开启代码支持;
     * Controller才能使用@RequiresPermissions
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        return shiroFilterFactoryBean;
    }



}