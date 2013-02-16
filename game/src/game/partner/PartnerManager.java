package game.partner;

import java.util.List;
import java.util.Map;

import game.battle.formation.IFormation;
import user.UserInfo;
import util.ErrorCode;

/**
 * 伙伴管理器
 * @author liukun
 * 2013-2-5 下午2:14:22
 */
public class PartnerManager {
	private final List<PartnerBase>				partners;
	private final UserInfo						user;
	private IFormation							formation;
	private PartnerDataProvider					db = PartnerDataProvider.getInstance();
	
	public PartnerManager(UserInfo user) {
		super();
		this.user = user;
		partners = db.getAll( user );
	}


	/**
	 * 穿装备
	 * @param parterId
	 * @param equipmentId
	 * @return
	 */
	ErrorCode dress( short parterId, long equipmentId ){
		return null;
		
	}
	
	/**
	 * 脱装备
	 * @param parterId
	 * @param equipmentId
	 * @return
	 */
	ErrorCode dressOff( short parterId, long equipmentId ){
		return null;
		
	}
	
	/**
	 * 计算此战士的各种加成
	 * @param partner
	 */
	void calcAbility( PartnerBase partner ){
		
	}


	public IFormation getFormation() {
		if( formation == null ){
			buildFormation();
		}
		return formation;
	}


	/**
	 * 构建战斗团队
	 */
	private void buildFormation() {
	}
	
	

}
