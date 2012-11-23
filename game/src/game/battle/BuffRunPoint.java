package game.battle;

/**
 * buf起作用时候的时间点
 * @author liukun
 * 2012-11-1 下午03:15:38
 */
public enum BuffRunPoint {
	/**
	 * 被攻击后,要扣除的HP已经计算出来，在正式扣除之前。
	 * 例子:料事如神
			令己方全体队员获得壁垒效果，在本回合受到的伤害降低80%
	 */
	AFTER_DEFENDING,
	
	/**
	 * 攻击前
	 * 例子：被人锁定住，若干回合不允许出招，或者中毒效果，自动减血
	 */
	BEFORE_ATTACK,
	
	/**
	 * 及时生效的
	 * 例子：某个技能发送后，附带增加30%攻击力的buff，只需要在若干回合后主动关闭
	 */
	NOW;
}
