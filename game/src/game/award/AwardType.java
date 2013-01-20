package game.award;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏内各种奖励类型
 * 
 * @author admin
 * 
 */
public enum AwardType {

	/**
	 * 金币
	 */
	CASH(1),

	/**
	 * 体力，控制玩家的战斗时间
	 */
	STRENGTH(2),
	
	/**
	 * 经验
	 */
	EXP(3),
	
	/**
	 * 道具
	 */
	PROP(4),
	
	/**
	 * 金币，点券
	 */
	GOLD(5);
	
	//abstract void accept( UserInfo user, int number, short propId, String funcName );

	private final byte number;

	AwardType(int n) {
		number = (byte) n;
	}

	private static final Map<Byte, AwardType> intToEnum = new HashMap<Byte, AwardType>();
	static {
		for (AwardType a : values()) {
			intToEnum.put(a.number, a);
		}
	}
	

	public static AwardType fromNumber(int n) {
		return intToEnum.get(n);
	}

	public byte toNumber() {
		return number;
	}

	public static void main(String[] args) {
		AwardType at = AwardType.CASH;
		System.out.println(at);
		AwardType at1 = AwardType.valueOf("STRENGTH");
		System.out.println(at1.toNumber());
	}
}