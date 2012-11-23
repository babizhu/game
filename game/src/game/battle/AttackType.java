package game.battle;


public enum AttackType {

	/**
	 * 回合开始
	 */
	BEGIN_ROUND(20), 
	SKILL_ATTACK(1), 
	NORMAL_ATTACK(2);
	
	
	private final byte number;
	AttackType( int n ) {
		number = (byte) n;
	}
	public byte toNumber() {
		// TODO Auto-generated method stub
		return number;
	}

}
