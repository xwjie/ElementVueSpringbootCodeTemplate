package cn.xiaowenjie.common.daos;

import cn.xiaowenjie.common.rbac.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *  角色 DAO
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
public interface RoleDao extends PagingAndSortingRepository<Role, Long> {

}