package game.bag;


/**
 * 单纯的bean信息，存放一些函数之间传递的参数--道具及数量
 * @author admin
 * 2012-9-28 下午05:30:39
 */
public class PropUnit {

	/**
	 * 道具ID
	 */
	private final short 		templetId;
	
	/**
	 * 道具数量
	 */
	private final short	count;
	
	
	public PropUnit( short templetId, short count ) {
		this.templetId = templetId;
		this.count = count;
	}
	
	public short getTempletId() {
		return templetId;
	}

//	public void setTempletId(short templetId) {
//		this.templetId = templetId;
//	}

	public short getCount() {
		return count;
	}
//
//	public void setCount(short count) {
//		this.count = count;
//	}

}
