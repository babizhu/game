package game.partner;

import game.battle.formation.IFormation;
import user.UserInfo;
import util.ErrorCode;

/**
 * 伙伴管理器
 * @author Administrator
 * 2013-2-5 下午2:14:22
 */
public class PartnerManager {
	private final UserInfo			user;
	private IFormation				formation;
	
	public PartnerManager(UserInfo user) {
		super();
		this.user = user;
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
	void calcAbility( Partner partner ){
		
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
