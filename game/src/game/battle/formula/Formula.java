package game.battle.formula;

import util.RandomUtil;
import game.fighter.BaseFighter;

public enum Formula {
	/**
	 * 普通攻击的计算公式
	 * 无参数
	 */
	NormalAttackFormula {
		@Override
		public
		int run(BaseFighter attacker, BaseFighter defender, float[] arguments) {
			int r = RandomUtil.getRandomInt( 0, 40 );//伤害波动值，要求在-20~+20之间
			r -= 20;
			r = 0;//取消随机对测试的影响
			float damage = attacker.getPhyAttack() * ( 1f - ( defender.getPhyDefend()/( defender.getPhyDefend() + 10000f ) ) );
			damage *= 1f + r / 100f;
			damage *= ((float)attacker.getLevel() + 10)/ ((float)defender.getLevel() + 10);
			return (int) damage;
		}
	},
	
	/**
	 * 技能攻击伤害的计算公式
	 * 参数意义:	
	 * 		arguments[0] 普通伤害的倍数	
	 */
	SkillAttackFormula {
		@Override
		public
		int run(BaseFighter attacker, BaseFighter defender, float[] arguments) {
			int r = RandomUtil.getRandomInt( 0, 40 );//伤害波动值，要求在-20~+20之间
			r -= 20;
			r = 0;//取消随机对测试的影响
			float damage = attacker.getPhyAttack() * ( 1f - ( defender.getPhyDefend()/( defender.getPhyDefend() + 10000f ) ) );
			damage *= 1f + r / 100f;
			damage *= ((float)attacker.getLevel() + 10)/ ((float)defender.getLevel() + 10);
			damage *= arguments[0];//技能攻击，伤害直接*相应参数
			return (int) damage;
		}
	},
	/**
	 * 按比例恢复hp<br>
	 * 参数意义:
	 * 		arguments[0]==1则按照攻击者的hpMax进行计算，否则按照防守者的hpMax进行计算<br>
	 * 		arguments[1] 计算比例
	 * 
	 */
	HpFormula{

		@Override
		public int run( BaseFighter attacker, BaseFighter defender, float[] arguments ) {
			if( arguments[0] == 1 ){
				return (int) (attacker.getHpMax() * arguments[1]);
			}
			return (int) (defender.getHpMax() * arguments[1]);
		}
		
	},
	/**
	 * 输入多少返回多少<br>
	 * 参数意义：arguments[0]为输入的数值
	 */
	DirectOutputFormula{

		@Override
		public int run(BaseFighter attacker, BaseFighter defender, float[] arguments) {
			return (int) arguments[0];
		}
		
	};
	public abstract int run( BaseFighter attacker, BaseFighter defender, float[] arguments );
	

}
