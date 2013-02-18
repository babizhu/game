package game.prop.equipment;

import game.prop.IpropManager;
import game.prop.PropDataProvider;
import game.prop.PropUnit;

import java.util.Map;

import user.UserInfo;
import util.ErrorCode;

/**
 * 道具管理器
 * @author liukun
 * 2013-1-31 下午2:17:20
 */
public class EquipmentManager implements IpropManager {

	/**
	 * long for equipment id,
	 */
	private final Map<Long,Equipment> 	equipments;
	private final PropDataProvider		db = PropDataProvider.getInstance();
	private final UserInfo				user;
	
	public EquipmentManager( UserInfo user ) {
		this.user = user;
		equipments = db.getAllEquipments( user.getName() );
	}

	/**
	 * 是否有必要考虑同时加入多件装备的功能需求，这里虽然已经实现了此功能，但感觉代码难度增加，出错几率增大，是否值得？
	 * 有可能存在部分写入数据库的问题
	 */
	@Override
	public ErrorCode add( PropUnit unit ) {
		for( int i = 0; i < unit.getCount(); i++ ){
			Equipment e = new Equipment( unit.getTemplet() );
			ErrorCode code = db.addEquipment( e, user.getName() );
			
			if( code == ErrorCode.SUCCESS ){
				equipments.put( e.getId(), e );
			}
			else{
				return code;
			}
		}
		return ErrorCode.SUCCESS;
	}

	@Override
	public ErrorCode remove( PropUnit unit ) {
		long id = unit.getPropId();
		Equipment equipment = equipments.get( id );
		if( equipment == null ){
			return ErrorCode.PROP_NOT_FOUNTD;
		}
		return db.removeEquipment( equipment );
	}

	@Override
	public int calcNeedGridCount( PropUnit unit ) {
		return unit.getCount();
	}

	@Override
	public int getGridCount() {
		return equipments.size();
	}

	public Equipment getEquipmentById(long propId) {
		return equipments.get( propId );
	}

	@Override
	public ErrorCode checkPropIsEnough(PropUnit unit) {
		return null;
	}
	
	/**
	 * 装备升级
	 * @param id
	 * @return
	 */
	public ErrorCode levelUp( long id ){
		Equipment e = equipments.get( id );
		if( e == null ){
			return ErrorCode.PROP_NOT_FOUNTD;
		}
		return ErrorCode.SUCCESS;
	}

	
}
