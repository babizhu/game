package game.prop;

import game.partner.PartnerBase;
import game.prop.equipment.Equipment;
import game.prop.equipment.EquipmentBase;
import game.prop.equipment.EquipmentManager;
import game.prop.stuff.StuffPropManager;


import user.UserInfo;
import util.ErrorCode;

/**
 * 总的道具管理类,
 * @author liukun
 * 2013-1-31 下午3:06:06
 */
public class PropManager {

	private final EquipmentManager 	equipmentManager;
	private final StuffPropManager 	stuffManager;
	
	/**
	 * 空闲的剩余格子数目
	 */
	private int						freeGridCount;
	
	public PropManager( UserInfo user ) {
		super();
		stuffManager = new StuffPropManager( user );
		equipmentManager = new EquipmentManager( user );
		freeGridCount = user.getBagCapacity() - equipmentManager.getGridCount() - stuffManager.getGridCount();
	}


	public ErrorCode add( PropUnit unit ){
		IpropManager m = getManager( unit );
		int needGridCount = m.calcNeedGridCount( unit ); 
		if( needGridCount > freeGridCount ){
			return ErrorCode.BAG_IS_FULL;
		}
		ErrorCode code = m.add( unit ); 
		if( code == ErrorCode.SUCCESS ){
			freeGridCount -= needGridCount;//更新剩余的空闲格子数目
		}
		return code;
	}
	
	public ErrorCode remove( PropUnit unit ){
		return getManager(unit).remove(unit);
	}
	
	private IpropManager getManager( PropUnit unit ){
		switch( unit.getTemplet().getType() )
		{
		case STUFF:
			return stuffManager;
		case EQUIPMENT:
			return equipmentManager;
		default:
			return equipmentManager;
		}
		
	}

	public int	getFreeGridCount(){
		return freeGridCount;
	}
	public Equipment getEquipmentById(long propId) {
		return equipmentManager.getEquipmentById( propId );
	}
	
	
	/**
	 * 道具升级，可能会包括装备，宝石等，参数会根据实际需求进行调整
	 * @param partner		此装备所在的伙伴，如无，用null代替
	 * @param equipment
	 * @return
	 */
	public ErrorCode levelUp( PartnerBase partner, EquipmentBase equipment ){
//		return equipmentManager.levelUp( propId );
		return equipment.levelUp();
		
	}
	
	public ErrorCode mix( long propId ){
		return null;		
	}
	
		
}
