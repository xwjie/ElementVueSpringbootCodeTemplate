package cn.xiaowenjie.services;

import cn.xiaowenjie.beans.Favorite;
import cn.xiaowenjie.beans.Favorite;
import cn.xiaowenjie.common.beans.PageResp;
import cn.xiaowenjie.common.consts.Roles;
import cn.xiaowenjie.common.utils.UserUtil;
import cn.xiaowenjie.daos.FavoriteDao;
import cn.xiaowenjie.daos.FavoriteDao;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

import static cn.xiaowenjie.common.utils.CheckUtil.*;

/**
 * 配置业务处理类
 *
 * @author 肖文杰 https://github.com/xwjie/
 */
@Service
@Slf4j
public class FavoriteService {

    @Autowired
    FavoriteDao dao;

    public Collection<Favorite> getAll(int type) {
        // 校验通过后打印重要的日志
        log.info("getAll start ...");

        List<Favorite> data = dao.findAllByObjType(type);

        log.info("getAll end, data size:" + data.size());

        return data;
    }

    /**
     * 增加配置，需要管理员权限
     *
     * @param favorite
     * @return
     */
    public long add(Favorite favorite) {
        // 参数校验
        notNull(favorite);

        check(favorite.getObjType() > 0);
        check(favorite.getObjId() > 0L);

        // 校验通过后打印重要的日志
        log.info("add favorite:" + favorite);

        long userId = UserUtil.getUserId();

        // 校验重复
        Favorite favoriteNew = dao.findByUserIdAndObjTypeAndObjId(userId, favorite.getObjType(), favorite.getObjId());

        // 如果没有记录，就新增
        if (favoriteNew == null) {
            // 设置用户id
            favorite.setUserId(userId);

            favoriteNew = dao.save(favorite);

            // 修改操作需要打印操作结果
            log.info("add favorite success, id:" + favoriteNew.getId());
        }

        return favoriteNew.getId();
    }

    /**
     * 根据id删除配置项
     * <p>
     * 管理员或者自己创建的才可以删除掉
     *
     * @param id
     * @return
     */
    public boolean delete(long id) {
        Favorite favorite = dao.findOne(id);

        // 参数校验
        check(favorite != null, "id.error", id);

        // 判断是否可以删除
        check(canDelete(favorite), "no.permission");

        dao.delete(id);

        // 修改操作需要打印操作结果
        log.info("delete favorite success, id:" + id);

        return true;
    }

    /**
     * 自己创建的或者管理员可以删除数据
     * 判断逻辑变化可能性大，抽取一个函数
     *
     * @param favorite
     * @return
     */
    private boolean canDelete(Favorite favorite) {
        return UserUtil.getUserId() == favorite.getUserId() || UserUtil.isAdmin();
    }

}
