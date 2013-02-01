package game.prop;


import game.prop.templet.BasePropTemplet;


/**
 * 单纯的bean信息，存放一些函数之间传递的参数--道具及数量
 * @author admin
 * 2012-9-28 下午05:30:39
 */
public class PropUnit {

	private final long				propId;   
	/**
	 * 道具模板
	 */

	private final BasePropTemplet 	templet;

	
	/**
	 * 道具数量
	 */

	private final short	count;

	
	

	public PropUnit( BasePropTemplet templet, short count, long propId ) {
		this.templet = templet;
		this.count = count;
		this.propId = propId;
	}
	


//	public void setTempletId(short templetId) {
//		this.templetId = templetId;
//	}

	public short getCount() {
		return count;
	}

	
	public long getPropId() {
		return propId;
	}



	public BasePropTemplet getTemplet() {
		return templet;
	}
	
	

//
//	public void setCount(short count) {
//		this.count = count;
//	}

}
