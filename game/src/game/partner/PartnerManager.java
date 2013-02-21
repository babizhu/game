package game.partner;

import game.battle.auto.Formation9;
import game.battle.formation.IFormation;
import game.prop.PropManager;
import game.prop.equipment.EquipmentBase;

import java.util.List;

import user.UserInfo;
import util.ErrorCode;

/**
 * 伙伴管理器
 * @author liukun
 * 2013-2-5 下午2:14:22
 */
public class PartnerManager {
	private final List<PartnerBase>				partners;
//	private final UserInfo						user;
	private final PropManager					propManager;
	//private IFormation							formation = null;
	private PartnerDataProvider					db = PartnerDataProvider.getInstance();
	
	public PartnerManager(UserInfo user) {
		super();
		//this.user = user;
		propManager = user.getPropManager();
		partners = db.getAll( user );
		
	}


	/**
	 * 穿装备
	 * @param parterId
	 * @param equipmentId
	 * @return
	 */
	ErrorCode dress( long parterId, long propId ){
		
		PartnerBase p = getPartnerById( parterId );
		if( p == null ){
			return ErrorCode.PARTNER_NOT_FOUND;
		}
		
		EquipmentBase e = propManager.getEquipmentById( propId );
		if( e == null ){
			return ErrorCode.PROP_NOT_FOUNTD;
		}

		ErrorCode code = p.dress( e );
//		if( code == ErrorCode.SUCCESS ){
//			p.
//		}
		return code;
	}
	
	/**
	 * 脱装备
	 * @param parterId
	 * @param equipmentId
	 * @return
	 */
	ErrorCode dressOff( long parterId, long equipmentId ){
		return null;
		
	}
	
	/**
	 * 计算此战士的各种加成
	 * @param partner
	 */
	void calcAbility( PartnerBase partner ){
		
	}

	public PartnerBase getPartnerById( long id ){
		for( PartnerBase p : partners ){
			if( p.getId() == id ){
				return p;
			}
		}
		return null;
	}

	public IFormation getFormation( boolean isLeft ) {
		IFormation f = new Formation9( null, isLeft, null );
		return f;
	}


	
	
	

}
