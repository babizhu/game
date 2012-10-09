package game.bag;

import game.prop.IProp;

/**
 * 单纯的bean信息，存放一些函数之间传递的参数--道具及数量
 * @author admin
 * 2012-9-28 下午05:30:39
 */
public class PropUnit {

	/**
	 * 道具ID
	 */
	IProp	prop;
	
	/**
	 * 道具数量
	 */
	short	count;
	
	public PropUnit( IProp prop, short count ) {
		this.prop = prop;
		this.count = count;
	}
}
