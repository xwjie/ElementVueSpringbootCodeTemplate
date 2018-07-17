package cn.xiaowenjie.daos;

import cn.xiaowenjie.beans.User;
import cn.xiaowenjie.tool.EndData;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 用户DAO
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
public interface EndDataDao extends PagingAndSortingRepository<EndData, Long> {

    List<EndData> findAllByRecordId(long uploadRecordId);
}