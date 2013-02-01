package game.prop.equipment;

import game.prop.IBag;
import game.prop.PropDataProvider;
import game.prop.PropUnit;
import game.prop.templet.EquipmentTemplet;

import java.util.Map;

import util.ErrorCode;

/**
 * 道具管理器
 * @author Administrator
 * 2013-1-31 下午2:17:20
 */
public class EquipmentManager implements IBag {

	/**
	 * long for equipment id,
	 */
	private Map<Long,Equipment> equipments;
	private final PropDataProvider	db = PropDataProvider.getInstance();
	
	public EquipmentManager(String uname) {
		equipments = db.getAllEquipments( uname );
	}

	public ErrorCode add( PropUnit unit, String uname ) {
		Equipment e = db.addEquipment( (EquipmentTemplet) unit.getTemplet(), uname );
		if( e != null ){
			equipments.put( e.getId(), e );
		}
		return ErrorCode.SUCCESS;
	}

	public static IEquipment getEquipmentById(long propId) {
		return null;
	}
	
	public ErrorCode remove( long id ) {
		Equipment equipment = equipments.get( id );
		if( equipment == null ){
			return ErrorCode.PROP_NOT_FOUNTD;
		}
		return db.removeEquipment( equipment );
	}

	@Override
	public int calcNeedGridCount(PropUnit unit) {
		return equipments.size() + unit.getCount();
	}

}
