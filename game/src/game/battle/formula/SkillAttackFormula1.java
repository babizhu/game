package game.battle.formula;

import util.RandomUtil;
import game.fighter.BaseFighter;

public class SkillAttackFormula1 implements IFormula{

	private static SkillAttackFormula1 instance = new SkillAttackFormula1();
	public static SkillAttackFormula1 getInstance(){
		return instance;
	}
	private SkillAttackFormula1(){}
	/**
	 * 技能攻击伤害的计算公式
	 */
	@Override
	public int run( BaseFighter attacker, BaseFighter defender, Object arguments ) {

		int r = RandomUtil.getRandomInt( 0, 40 );//伤害波动值，要求在-20~+20之间
		r -= 20;
		r = 0;//取消随机对测试的影响
		float damage = attacker.getPhyAttack() * ( 1f - ( defender.getPhyDefend()/( defender.getPhyDefend() + 10000f ) ) );
		damage *= 1f + r / 100f;
		damage *= ((float)attacker.getLevel() + 10)/ ((float)defender.getLevel() + 10);
		damage *= 2;//技能攻击，伤害直接*2
		return (int) damage;
	}

}
