package cn.xiaowenjie.controllers;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.xiaowenjie.common.rbac.User;
import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.common.utils.UserUtil;
import cn.xiaowenjie.services.UserService;

/**
 * app相关的controller，支持跨域
 *
 * @author 肖文杰 https://github.com/xwjie/
 */
@RequestMapping("/app")
@RestController
@CrossOrigin
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public ResultBean<User> login(HttpSession session, @RequestParam String username, @RequestParam String
            password) {
        logger.info("login user:" + username);

        User user = userService.login(username, password);

        //FIXME
        session.setAttribute(UserUtil.KEY_USER, user);

        return new ResultBean<User>(user);
    }

    @PostMapping(value = "/user")
    public ResultBean<User> login(HttpSession session) {
        logger.info("get current user");
        return new ResultBean<User>(UserUtil.getUserIfLogin());
    }

    @PostMapping(value = "/logout")
    public ResultBean<Boolean> logout(HttpSession session) {
        //session.invalidate();
        userService.logout();

        return new ResultBean<Boolean>(true);
    }
}
