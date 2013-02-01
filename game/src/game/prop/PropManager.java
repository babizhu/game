package game.prop;

import game.prop.equipment.EquipmentManager;
import game.prop.stuff.StuffPropManager;
import user.UserInfo;
import util.ErrorCode;

/**
 * 总的道具管理类
 * @author Administrator
 * 2013-1-31 下午3:06:06
 */
public class PropManager {

	private final EquipmentManager 	euipments;
	private final StuffPropManager 	stuffs;
	private final UserInfo			user;
	
	
	
	public PropManager( UserInfo user ) {
		super();
		this.user = user;
		stuffs = new StuffPropManager( user.getName() );
		euipments = new EquipmentManager( user.getName() );
	}


	ErrorCode add( PropUnit unit ){
		
		if( unit.getTemplet().isEquipment() ){
			
			return euipments.add( unit, user.getName() );
		}
		else{
			return stuffs.add( unit, user.getName() );
		}
		
	}
}
