package game.award;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏内各种奖励类型
 * 
 * @author admin
 * 
 */
public enum AwardContent {

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

	AwardContent(int n) {
		number = (byte) n;
	}

	private static final Map<Byte, AwardContent> intToEnum = new HashMap<Byte, AwardContent>();
	static {
		for (AwardContent a : values()) {
			intToEnum.put(a.number, a);
		}
	}
	

	public static AwardContent fromNumber(int n) {
		return intToEnum.get((byte)n);
	}

	public byte toNumber() {
		return number;
	}

	public static void main(String[] args) {
		AwardContent at = AwardContent.CASH;
		System.out.println(at);
		AwardContent at1 = AwardContent.valueOf("STRENGTH");
		System.out.println(at1.toNumber());
	}
}