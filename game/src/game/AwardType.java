package game;

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
	MONEY(1),

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
	PROP(4);

	private final int number;

	AwardType(int n) {
		number = n;
	}

	private static final Map<Integer, AwardType> intToEnum = new HashMap<Integer, AwardType>();
	static {
		for (AwardType a : values()) {
			intToEnum.put(a.number, a);
		}
	}

	public static AwardType fromInt(int n) {
		return intToEnum.get(n);
	}

	public int toInt() {
		return number;
	}

	public static void main(String[] args) {
		AwardType at = AwardType.MONEY;
		System.out.println(at);
		AwardType at1 = AwardType.valueOf("STRENGTH");
		System.out.println(at1.toInt());
	}
}