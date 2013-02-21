package game.prop.equipment;

import game.prop.templet.GemTemplet;
import game.prop.templet.PropTempletBase;
import util.ErrorCode;

/**
 * 用于嵌套在装备上的宝石
 * @author Administrator
 * 2013-1-31 下午4:49:11
 */
public class Gem extends EquipmentBase {

	private final GemTemplet			templet;
	
	public Gem( PropTempletBase templet ){
		this.templet = (GemTemplet) templet;
	}
	
	@Override
	public PropTempletBase getTemplet() {
		return templet;
	}

	
	@Override
	public ErrorCode levelUp() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void calcAddtion() {
	}

	
	@Override
	public String toString() {
		return "Gem " + super.toString() + "]";
	}
}
