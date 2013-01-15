package game.battle;

import game.battle.auto.AttackInfo;
import game.battle.formula.Formula;
import game.fighter.BaseFighter;

import java.util.Comparator;

public interface IBattleUtil {

//
//	/**
//	 * 计算普通攻击的各种信息情况
//	 * @param attacker
//	 * @param defender
//	 * @return
//	 */
//	AttackInfo normalAttack(BaseFighter attacker, BaseFighter defender );
//
//	/**
//	 * 计算技能伤害
//	 * @param attacker
//	 * @param defender
//	 * @param formula
//	 * @return
//	 */
//	AttackInfo skillAttack(BaseFighter attacker, BaseFighter defender, IFormula formula );
//	
	/**
	 * 计算反击的伤害
	 * @param attacker
	 * @param defender
	 * @return
	 */
	int calcCounterAttackDamage( BaseFighter attacker, BaseFighter defender );
	
	/**
	 * 确定所有参战人员出手顺序的算法
	 */
	Comparator<BaseFighter> getOrderComparator();

	/**
	 * 根据公式计算伤害值
	 * @param attacker
	 * @param defender
	 * @param formula
	 * @param arguments
	 * @return
	 */
	AttackInfo calcAttackInfo( BaseFighter attacker, BaseFighter defender,	Formula formula, float[] arguments );


	

}
