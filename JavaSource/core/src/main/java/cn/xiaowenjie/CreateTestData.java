package cn.xiaowenjie;

import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.common.consts.Roles;
import cn.xiaowenjie.common.daos.RoleDao;
import cn.xiaowenjie.common.daos.UserDao;
import cn.xiaowenjie.common.rbac.Role;
import cn.xiaowenjie.common.rbac.User;
import cn.xiaowenjie.common.utils.UserUtil;
import cn.xiaowenjie.services.ConfigService;
import cn.xiaowenjie.services.UserService;
import cn.xiaowenjie.tool.PasswordUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 增加测试数据 （上线时候需要删除掉）
 *
 * @author 晓风轻
 */
@Component
@Slf4j
public class CreateTestData implements CommandLineRunner {

    @Autowired
    ConfigService configService;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userSevice;

    @Autowired
    RoleDao roleDao;

    @Override
    public void run(String... args) throws Exception {
        // 用户不存在则创建测试数据
        if (userDao.findByName("xwjie") == null) {
            log.error("创建测试数据.....");

            createUsers();

            // FIXME
            // createConfigs();

            log.error("创建测试数据完毕");
        }
    }

    public void createUsers() {
        log.error("---addUser---");

        // role
        Role normaleRole = new Role();

        normaleRole.setName(Roles.NORMAL_USER);
        normaleRole.setComment("普通用户");

        normaleRole = roleDao.save(normaleRole);


        Role adminRole = new Role();

        adminRole.setName(Roles.ADMIN);
        adminRole.setComment("管理员");

        adminRole = roleDao.save(adminRole);

        // amdin
        User admin = new User();

        admin.setName("xwjie");
        admin.setNick("晓风轻");

        // 盐和密码
        admin.setSalt("admin");
        String password = PasswordUtil.renewPassword("123456", admin.getSalt());

        // 计算后密码
        admin.setPassword(password);

        // 角色
        admin.setRoles(Lists.newArrayList(adminRole, normaleRole));

        userDao.save(admin);

        for (int i = 1; i <= 10; i++) {
            User user = new User();

            user.setName("user" + i);
            user.setNick("测试用户" + i);

            // 盐和密码
            user.setSalt(user.getName());
            String password2 = PasswordUtil.renewPassword("123456", user.getSalt());

            // 计算后密码
            user.setPassword(password2);

            user.setRoles(Lists.newArrayList(normaleRole));

            userDao.save(user);
        }
    }

    @Autowired
    SecurityManager securityManager;

    public void createConfigs() {
        log.error("---addTestData---");

        // 登陆
        userSevice.login("xwjie", "123456");

        //
        ThreadContext.bind(securityManager);

        for (int i = 1; i <= 20; i++) {

            Config config = new Config();

            config.setName("测试数据：" + i);
            System.out.println("测试数据：" + i);
            config.setValue("https://github.com/xwjie");
            config.setDescription("晓风轻：" + i);

            // 创建记录的用户
            config.setCreator(UserUtil.getUser());

            configService.add(config);
        }
    }
}

