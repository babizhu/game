package game.battle;

import game.battle.auto.AttackInfo;
import game.battle.formula.Formula;
import game.fighter.FighterBase;

import java.util.Comparator;

public interface IBattleUtil {


	/**
	 * 计算反击的伤害
	 * @param attacker
	 * @param defender
	 * @return
	 */
	int calcCounterAttackDamage( FighterBase attacker, FighterBase defender );
	
	/**
	 * 确定所有参战人员出手顺序的算法
	 */
	Comparator<FighterBase> getOrderComparator();

	/**
	 * 根据公式计算伤害值
	 * @param attacker
	 * @param defender
	 * @param formula
	 * @param arguments
	 * @return
	 */
	AttackInfo calcAttackInfo( FighterBase attacker, FighterBase defender,	Formula formula, float[] arguments );

}
