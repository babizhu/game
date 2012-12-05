package game.battle;


public enum AttackType {

	/**
	 * 回合开始
	 */
	BEGIN_ROUND(20), 
	
	/**
	 * 技能攻击
	 */
	SKILL_ATTACK(1),
	
	/**
	 * 普通攻击
	 */
	NORMAL_ATTACK(2),
	
	/**
	 * 宠物攻击
	 */
	PET_ATTACK(3),
	
	/**
	 * 反击
	 */
	COUNTER_ATTACK(4);
	
	
	private final byte number;
	AttackType( int n ) {
		number = (byte) n;
	}
	public byte toNumber() {
		// TODO Auto-generated method stub
		return number;
	}

}
