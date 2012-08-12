package game.packages;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 用注解的方式处理包的描述信息
 * @author liukun 
 *
 */
//@Target (value = { ElementType.PACKAGE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PacketDescrip {
	public String desc() default "";
}
