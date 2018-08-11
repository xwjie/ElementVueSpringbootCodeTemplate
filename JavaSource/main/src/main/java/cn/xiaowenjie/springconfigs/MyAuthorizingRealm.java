package cn.xiaowenjie.springconfigs;

import cn.xiaowenjie.common.rbac.Role;
import cn.xiaowenjie.common.rbac.User;
import cn.xiaowenjie.services.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MyAuthorizingRealm extends AuthorizingRealm {

    private final static Logger logger = LoggerFactory.getLogger(MyAuthorizingRealm.class);

    @Autowired
    private UserService userService;

    //shiro的权限配置方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        logger.info("权限配置-->doGetAuthorizationInfo");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        logger.info("----------------------------->" + principals.getPrimaryPrincipal());

        User user = (User) principals.getPrimaryPrincipal();

        for (Role role : user.getRoles()) {

            authorizationInfo.addRole(role.getName());

            // 如果有权限，应该增加所有角色对应的权限
            // authorizationInfo.addStringPermission()
        }

        logger.info("用户" + user.getName() + "具有的角色:" + authorizationInfo.getRoles());
        logger.info("用户" + user.getName() + "具有的权限：" + authorizationInfo.getStringPermissions());

        return authorizationInfo;
    }

    //shiro的身份验证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        logger.info("正在验证身份...");
        SimpleAuthenticationInfo info = null;

        //将token转换成UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //从转换后的token中获取用户名
        String username = upToken.getUsername();
        logger.info("----->" + username);

        //查询数据库，得到用户
        User user = userService.findUser(username);

        if (user == null) {
            return null;
        }

        //得到加密密码的盐值
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());

//        logger.info("加密密码的盐："+salt);
//        //得到盐值加密后的密码：只用于方便数据库测试，后期不会用到。
//        Object md = new SimpleHash("MD5",upToken.getPassword(),salt,1024);
//        logger.info("盐值加密后的密码："+md);

        info = new SimpleAuthenticationInfo(
                user, //用户名
                user.getPassword(), //密码
                salt, //加密的盐值
                getName()  //realm name
        );

        return info;
    }

}