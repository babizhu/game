package game.prop;

import game.partner.PartnerBase;
import game.prop.enums.PropType;
import game.prop.equipment.Equipment;
import game.prop.equipment.EquipmentBase;
import game.prop.equipment.EquipmentManager;
import game.prop.stuff.StuffManager;


import user.UserInfo;
import util.ErrorCode;

/**
 * 总的道具管理类,
 * @author liukun
 * 2013-1-31 下午3:06:06
 */
public class PropManager {

	private final EquipmentManager 		equipmentManager;
	private final StuffManager 			stuffManager;
	private final UserInfo				user;
	
	/**
	 * 空闲的剩余格子数目
	 */
	private int							freeGridCount;
	
	public PropManager( UserInfo user ) {
		super();
		stuffManager = new StuffManager( user );
		equipmentManager = new EquipmentManager( user );
		this.user = user;
		freeGridCount = user.getBagCapacity() - equipmentManager.getGridCount() - stuffManager.getGridCount();
	}


	public ErrorCode add( PropUnit unit ){
		IPropManager m = getManager( unit.getTemplet().getType() );
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
		PropType type = unit.getTemplet().getType();
		return getManager( type ).remove( unit );
	}
	
	private IPropManager getManager( PropType type ){
		switch( type )
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
	 * 这可能不行，升级的形式多种多样，接口不好处理，主要是参数
	 * 
	 * @param partner		此装备所在的伙伴，如无，用null代替，如果客户端传入恶意数据有无问题？
	 * @param equipment
	 * @return
	 */
	public ErrorCode levelUp( long partnerId, long equipmentIds ){
		PartnerBase partner = user.getPartnerManager().getPartnerById( partnerId );
		if( partner == null ){
			return ErrorCode.PARTNER_NOT_FOUND;
		}
		EquipmentBase equipment = null;
//		IPropManager m = getManager( unit );
////		return equipmentManager.levelUp( propId );
//		return m.levelUp();
		ErrorCode code = equipment.levelUp();
		if( code == ErrorCode.SUCCESS ){
			partner.calcAddtion();
		}
		return code;
		
	}
	
	public ErrorCode mix( long propId ){
		return null;		
	}
	
		
}
