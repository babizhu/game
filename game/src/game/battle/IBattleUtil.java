package game.battle;

import game.fighter.BaseFighter;

public interface IBattleUtil {

	/**
	 * 判断攻击是否命中
	 * @param attacker
	 * @param defender
	 * @return
	 */
	boolean isHit(BaseFighter attacker, BaseFighter defender);

	/**
	 * 计算暴击加成，返回1意味着没有暴击，2则为双倍暴击，以此类推
	 * @param attacker
	 * @param defender
	 * @return
	 */
	byte calcCrit( BaseFighter attacker, BaseFighter defender );

	/**
	 * 计算普通攻击的伤害
	 * @param attacker
	 * @param defender
	 * @return
	 */
	int calcNormalAttackDamage(BaseFighter attacker, BaseFighter defender );

	/**
	 * 计算是否格挡并反击，注意，为统一用词，还是计算attacker是否可以格挡并反击defender
	 * @param defender
	 * @param attacker
	 * @return
	 */
	boolean isBlockAndCounterAttack( BaseFighter defender, BaseFighter attacker );

}
