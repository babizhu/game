package game.prop.equipment;

import util.ErrorCode;

/**
 * 通常指的是具备唯一id，可升级的道具，也就是装备，例如圣衣，宝石等
 * @author Administrator
 * 2013-2-18 下午4:33:19
 */
public interface IEquipment{
	
	/**
	 * 装备升级，参数会根据实际情况进行改变
	 * @return
	 */
	ErrorCode levelUp();
	

}
