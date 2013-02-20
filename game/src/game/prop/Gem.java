package game.prop;

import util.ErrorCode;
import game.prop.equipment.IEquipment;
import game.prop.templet.PropTempletBase;

/**
 * 用于嵌套在装备上的宝石
 * @author Administrator
 * 2013-1-31 下午4:49:11
 */
public class Gem extends PropBase implements IEquipment, ICalculateAddtion{

	private	long		id;
	
	@Override
	public PropTempletBase getTemplet() {
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

	@Override
	public void buildContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calcAddtion() {
		// TODO Auto-generated method stub
		
	}
}
