package cn.xiaowenjie.beans;

import cn.xiaowenjie.common.rbac.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Data
@Table(
    uniqueConstraints=
        @UniqueConstraint(columnNames={"userId", "objType", "objId"})
)
@EqualsAndHashCode(callSuper=true)
@EntityListeners(AuditingEntityListener.class)
public class Favorite extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long userId;

	private int objType;

	private long objId;

}
