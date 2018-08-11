package cn.xiaowenjie.beans;

import cn.xiaowenjie.common.rbac.User;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@lombok.Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Blog extends BaseEntity{

    private String title;

    @Lob
    private String  body;

    /**
     * 创建者
     */
    @CreatedBy
    @ManyToOne
    private User creator;
}
