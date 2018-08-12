package cn.xiaowenjie.daos;

import cn.xiaowenjie.beans.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


/**
 *  配置类DAO
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
public interface FavoriteDao extends PagingAndSortingRepository<Favorite, Long> {
    List<Favorite> findAllByObjType(int type);

    Favorite findByUserIdAndObjTypeAndObjId(long userId, int objType, long objId);

    int countByObjTypeAndObjId(int objType, long objId);

    // @Query(value = "select t from Config t where t.name like %?1% or t.value like %?1% or t.description like %?1%", nativeQuery = false)
	// Page<Config> findAllByKeyword(String keyword, Pageable pageable);
}