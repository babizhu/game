package game.prop;

import game.prop.equipment.Equipment;
import game.prop.equipment.EquipmentManager;
import game.prop.stuff.StuffPropManager;
import user.UserInfo;
import util.ErrorCode;

/**
 * 总的道具管理类
 * @author liukun
 * 2013-1-31 下午3:06:06
 */
public class PropManager {

	private final EquipmentManager 	equipments;
	private final StuffPropManager 	stuffs;
	
	/**
	 * 空闲的剩余格子数目
	 */
	private int						freeGridCount;
	
	public PropManager( UserInfo user ) {
		super();
		stuffs = new StuffPropManager( user.getName() );
		equipments = new EquipmentManager( user.getName() );
		freeGridCount = user.getBagCapacity() - equipments.getGridCount() - stuffs.getGridCount();
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
		if( unit.getTemplet().isEquipment() ){
			return equipments;
		}
		else{
			return stuffs;
		}
	}

	public int	getFreeGridCount(){
		return freeGridCount;
	}
	public Equipment getEquipmentById(long propId) {
		return equipments.getEquipmentById( propId );
	}
	
	/**
	 * 道具升级，可能会包括装备，宝石等，参数会根据实际需求进行调整
	 * @param propId
	 * @return
	 */
	public ErrorCode levelUp( long propId ){
		return equipments.levelUp( propId );
		
	}
	
	public ErrorCode mix( long propId ){
		return null;
		
	}
		
}
