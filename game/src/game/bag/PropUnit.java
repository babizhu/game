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
	private IProp	prop;
	
	/**
	 * 道具数量
	 */
	private short	count;
	
	
	public PropUnit( IProp prop, short count ) {
		this.prop = prop;
		this.count = count;
	}
	
	public IProp getProp() {
		return prop;
	}



	public void setProp(IProp prop) {
		this.prop = prop;
	}



	public short getCount() {
		return count;
	}



	public void setCount(short count) {
		this.count = count;
	}

}
