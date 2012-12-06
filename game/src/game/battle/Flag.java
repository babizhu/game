package game.battle;

/**
 * 攻击和防御的标识符号
 * 
 * 用一个byte的八位来标识八种情况分别如下
 * 8	7	6	5	4	3	2		1
 *                          暴击   	 格挡
 * @author admin
 * 2012-12-6 上午11:53:24
 */
public enum Flag {
	/**
	 * 格挡并反击
	 */
	BLOCK(1),
	/**
	 * 暴击
	 */
	CRIT(2);
	
	private final byte number;
	Flag( int n ) {
		number = (byte) n;
	}
	public byte toNumber() {
		return number;
	}

}
