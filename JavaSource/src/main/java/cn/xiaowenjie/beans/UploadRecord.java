package cn.xiaowenjie.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 *  上传记录
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@EntityListeners(AuditingEntityListener.class)
public class UploadRecord extends BaseEntity {

    private String name;

    private String realPath;

    /**
     * 文件大小
     */
    private long size;

    /**
     *  文件包含的数据量
     */
    private  int dataCount;
}
