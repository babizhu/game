package game.packages;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * �԰��ļ����������������ӡ����
 * @author admin
 *
 */
//@Target (value = { ElementType.PACKAGE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PacketDescrip {
	public String desc() default "";
}
