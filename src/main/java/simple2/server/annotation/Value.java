package simple2.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Value
 * @Description 用于解析配置文件的注解
 * @Author yuanlw
 * @Date 2023/6/27 14:22
 * @Version 1.0
 **/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
}
