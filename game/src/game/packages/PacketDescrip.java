package game.packages;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 对包文件进行描述，方便打印出来
 * @author admin
 *
 */
//@Target (value = { ElementType.PACKAGE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PacketDescrip {
	public String desc() default "";
}
