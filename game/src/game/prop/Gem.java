package game.prop;

import util.ErrorCode;
import game.prop.equipment.IEquipment;
import game.prop.templet.BasePropTemplet;

/**
 * 用于嵌套在装备上的宝石
 * @author Administrator
 * 2013-1-31 下午4:49:11
 */
public class Gem extends BaseProp implements IEquipment{

	private	long		id;
	
	@Override
	public BasePropTemplet getTemplet() {
		return null;
	}

	public long getId() {
		return id;
	}

	@Override
	public ErrorCode levelUp() {
		// TODO Auto-generated method stub
		return null;
	}
}
