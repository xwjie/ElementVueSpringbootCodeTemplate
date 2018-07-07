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

}