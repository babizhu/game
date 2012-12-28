package game.battle;

import java.util.Comparator;

import game.battle.auto.AttackInfo;
import game.fighter.BaseFighter;

public interface IBattleUtil {


	/**
	 * 计算普通攻击的各种信息情况
	 * @param attacker
	 * @param defender
	 * @return
	 */
	AttackInfo normalAttack(BaseFighter attacker, BaseFighter defender );
	
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

	

}
