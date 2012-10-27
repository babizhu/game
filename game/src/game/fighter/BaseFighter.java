package game.fighter;

import game.prop.EquipmentManager;
import game.prop.IEquipment;
import game.prop.PropDataManager;
import game.prop.templet.EquipmentTemplet;
import util.ErrorCode;

public class BaseFighter implements IFighter {

	/**
	 * 等级
	 */
	short	level;
	
	@Override
	public ErrorCode dress(long oldPropId, long newPropId) {
//		
//		IEquipment equipment = EquipmentManager.getEquipmentById( newPropId );
//		if( equipment == null ){
//			return ErrorCode.PROP_NOT_FOUNTD;
//		}
//		
//		if( canDress( equipment ) == ErrorCode.SUCCESS ){
//			
//		}
		synchronized ( PropDataManager.class ) {
			
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 测试是否可以装备此道具
	 * @param equipment
	 * @return
	 */
	private ErrorCode canDress( IEquipment equipment ){
		EquipmentTemplet t = (EquipmentTemplet) equipment.getTemplet();
		if( t.getRequiredLevel() > getLevel() ){
			return ErrorCode.FIGHTER_LEVEL_NOT_ENOUGH;
		}
		
		//其他检测
		return ErrorCode.SUCCESS;
	}

	@Override
	public short getLevel() {
		return level;
	}

}
