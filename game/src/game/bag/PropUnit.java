package game.bag;

/**
 * 单纯的bean信息，存放一些函数之间传递的参数--道具的类型ID以及数量
 * @author admin
 * 2012-9-28 下午05:30:39
 */
public class PropUnit {

	/**
	 * 道具ID
	 */
	short	typeId;
	
	/**
	 * 道具数量
	 */
	short	count;
	
	public PropUnit( short typeId, short count ) {
		this.typeId = typeId;
		this.count = count;
	}
}
