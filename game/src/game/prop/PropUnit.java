package game.prop;

import game.prop.cfg.PropTempletCfg;
import game.prop.templet.PropTempletBase;




/**
 * 单纯的bean信息，存放一些函数之间传递的参数--道具及数量，如果是装备还包含装备id
 * @author admin
 * 2012-9-28 下午05:30:39
 */
public class PropUnit {

	private final long				propId;   
	/**
	 * 道具模板
	 */

	private final PropTempletBase 	templet;
	
	//private final short				templetId;

	
	/**
	 * 道具数量
	 */

	private final int				count;

	
	public PropUnit( short templetId, int count ){
		//this.templetId = templetId;
		this.templet = PropTempletCfg.getTempletById( templetId );
//		 = templet;
		this.count = count;
		this.propId = 0;
	}

//	public PropUnit( PropBaseTemplet templet, int count, long propId ) {
////		this.templet = templet;
//		this.count = count;
//		this.propId = propId;
//	}
	


//	public void setTempletId(short templetId) {
//		this.templetId = templetId;
//	}

	public int getCount() {
		return count;
	}

	
	public long getPropId() {
		return propId;
	}

//	public short getTempletId() {
//		return templetId;
//	}



	public PropTempletBase getTemplet() {
		return templet;
	}
	
	

//
//	public void setCount(short count) {
//		this.count = count;
//	}

}
