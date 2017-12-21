package cn.xiaowenjie.daos;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.xiaowenjie.beans.User;

/**
 * 用户DAO
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
public interface UserDao extends PagingAndSortingRepository<User, Long> {

	User findByName(String username);
}