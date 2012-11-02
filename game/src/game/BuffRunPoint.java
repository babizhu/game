package game;

/**
 * buf起作用时候的时间点
 * @author admin
 * 2012-11-1 下午03:15:38
 */
public enum BuffRunPoint {
	/**
	 * 被攻击时,要扣除的HP已经计算出来，在正式扣除之前。
	 * 例子:料事如神
			令己方全体队员获得壁垒效果，在本回合受到的伤害降低80%
	 */
	DEFENDING,
	
	BEFORE_ATTACK,//攻击前，例子：被人锁定住，若干回合不允许出招
}
