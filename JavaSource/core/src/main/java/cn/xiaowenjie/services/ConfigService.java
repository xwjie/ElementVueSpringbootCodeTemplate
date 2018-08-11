package cn.xiaowenjie.services;

import static cn.xiaowenjie.common.utils.CheckUtil.check;
import static cn.xiaowenjie.common.utils.CheckUtil.notEmpty;
import static cn.xiaowenjie.common.utils.CheckUtil.notNull;

import java.util.Collection;
import java.util.List;

import cn.xiaowenjie.common.consts.Roles;
import cn.xiaowenjie.common.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.common.beans.PageResp;
import cn.xiaowenjie.daos.ConfigDao;

/**
 * 配置业务处理类
 *
 * @author 肖文杰 https://github.com/xwjie/
 */
@Service
@Slf4j
public class ConfigService {

    @Autowired
    ConfigDao dao;

    public Collection<Config> getAll() {
        // 校验通过后打印重要的日志
        log.info("getAll start ...");

        List<Config> data = Lists.newArrayList(dao.findAll());

        log.info("getAll end, data size:" + data.size());

        return data;
    }

    /**
     *  增加配置，需要管理员权限
     * @param config
     * @return
     */
    @RequiresRoles(Roles.ADMIN)
    public long add(Config config) {
        // 参数校验
        notNull(config, "param.is.null");
        notEmpty(config.getName(), "name.is.null");
        notEmpty(config.getValue(), "value.is.null");

        // 校验通过后打印重要的日志
        log.info("add config:" + config);

        // 校验重复
        check(null == dao.findByName(config.getName()), "name.repeat");

        config = dao.save(config);

        // 修改操作需要打印操作结果
        log.info("add config success, id:" + config.getId());

        return config.getId();
    }

    /**
     *  根据id删除配置项
     *
     *  管理员或者自己创建的才可以删除掉
     * @param id
     * @return
     */
    @RequiresRoles(Roles.ADMIN)
    public boolean delete(long id) {

        Config config = dao.findOne(id);

        // 参数校验
        check(config != null, "id.error", id);

        // 判断是否可以删除
        check(canDelete(config), "no.permission");

        dao.delete(id);

        // 修改操作需要打印操作结果
        log.info("delete config success, id:" + id);

        return true;
    }

    /**
     * 自己创建的或者管理员可以删除数据
     * 判断逻辑变化可能性大，抽取一个函数
     *
     * @param config
     * @return
     */
    private boolean canDelete(Config config) {
        return UserUtil.getUser().equals(config.getCreator()) || UserUtil.isAdmin();
    }

    /**
     *  分页查找
     *
     * @param pageable
     * @param keyword
     * @return
     */
    public PageResp<Config> listPage(Pageable pageable, String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return new PageResp<Config>(dao.findAll(pageable));
        } else {
            // 也可以用springjpa 的 Specification 来实现查找
            return new PageResp<>(dao.findAllByKeyword(keyword, pageable));
        }
    }

    public Config getByName(String name) {
        return dao.findByName(name);
    }

}
