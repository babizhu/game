package util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 用注解的方式处理信息码的描述信息
 * @author liukun 
 *
 */
//@Target (value = { ElementType.PACKAGE })
@Retention(value = RetentionPolicy.SOURCE)
public @interface ErrorCodeDescrip {
	/**
	 * 错误码描述
	 * @return
	 */
	public String desc() default "";
}
