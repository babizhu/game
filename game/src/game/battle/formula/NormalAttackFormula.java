package game.battle.formula;

import util.RandomUtil;
import game.fighter.BaseFighter;

public class NormalAttackFormula implements IFormula{

	private static NormalAttackFormula instance = new NormalAttackFormula();
	public static NormalAttackFormula getInstance(){
		return instance;
	}
	private NormalAttackFormula(){}
	/**
	 * 普通攻击伤害的计算公式
	 */
	@Override
	public int run( BaseFighter attacker, BaseFighter defender, float[] arguments ) {

		int r = RandomUtil.getRandomInt( 0, 40 );//伤害波动值，要求在-20~+20之间
		r -= 20;
		r = 0;//取消随机对测试的影响
		float damage = attacker.getPhyAttack() * ( 1f - ( defender.getPhyDefend()/( defender.getPhyDefend() + 10000f ) ) );
		damage *= 1f + r / 100f;
		damage *= ((float)attacker.getLevel() + 10)/ ((float)defender.getLevel() + 10);
		return (int) damage;
	}

}
