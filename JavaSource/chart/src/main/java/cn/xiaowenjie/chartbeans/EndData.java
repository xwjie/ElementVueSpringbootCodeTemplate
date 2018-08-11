package cn.xiaowenjie.chartbeans;

import cn.xiaowenjie.beans.BaseEntity;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * 最终记录
 */
@Entity
@lombok.Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class EndData extends BaseEntity {
    /**
     * 关联的日志文件id
     */
    private long recordId;


    private String date ;

    private int volume;

}
