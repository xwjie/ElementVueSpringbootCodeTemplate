package cn.xiaowenjie.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  日志信息注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    // 操作
    String action();

    // 对象类型
    String itemType() default "";

    // 对象标识
    String itemId() default  "";

    // 其他参数
    String param() default "";
}
