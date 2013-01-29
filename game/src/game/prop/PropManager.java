package game.prop;

import game.prop.templet.BasePropTemplet;
import util.ErrorCode;

public class PropManager {

	EquipmentManager e;
	StuffPropManager s;
	
	ErrorCode put( PropUnit unit ){
		
		if( unit.getTemplet().isEquipment() ){
			return e.put( unit );
		}
		else{
			
		}
		return null;
		
	}
	
	public boolean canPut( PropUnit unit ){
		BasePropTemplet t = unit.getTemplet();
		
		return true;
	}
}
