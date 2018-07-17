package cn.xiaowenjie.daos;

import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.beans.UploadRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *  上传记录DAO
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
public interface UploadRecordDao extends PagingAndSortingRepository<UploadRecord, Long> {

    @Query(value = "select t from UploadRecord t where t.name like %?1% order by t.createTime desc", nativeQuery = false)
    Page<UploadRecord> findAllByKeyword(String keyword, Pageable pageable);

    /**
     *  分页查找所有记录，按创建时间倒序
     * @param pageable
     * @return
     */
    Page<UploadRecord> findAllByOrderByCreateTimeDesc(Pageable pageable);

    UploadRecord findByName(String name);
}