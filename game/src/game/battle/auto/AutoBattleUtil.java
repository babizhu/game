package game.battle.auto;


import game.battle.IBattleUtil;
import game.fighter.BaseFighter;
import util.RandomUtil;

public class AutoBattleUtil implements IBattleUtil {

	public static final IBattleUtil INSTANCE = new AutoBattleUtil();
	private AutoBattleUtil() {
	}
	
	@Override
	public boolean isBlockAndCounterAttack( BaseFighter attacker, BaseFighter defender ) {
		int r = RandomUtil.getRandomInt( 0, 100 );//随机值
		r = 50;//取消随机对测试的影响
		float result = (float)(attacker.getBlock() + 500) / (defender.getUnBlock() + 500) - 1;
		result *= 100;
		return result > r;	
	}
	/**
	 * 判断攻击者是否命中<br>
	 * 公式				命中率 = （自身命中等级+500）/(敌方闪避等级+500)    随机一个数字（1~100），随机数小于等于命中值 即为命中			
	 * @param attacker			攻击者
	 * @param defender			防御者
	 * @return
	 * 			true		命中
	 * 			false		未命中
	 * 
	 */
	@Override
	public boolean isHit( BaseFighter attacker, BaseFighter defender ) {
		int r = RandomUtil.getRandomInt( 0, 100 );//随机值
		r = 50;//取消随机对测试的影响
		float result = (float)(attacker.getHitRate() + 500) / (defender.getDodgeRate() + 500);
		result *= 100;
		return result > r;
	}
	

	@Override
	/**
	 *	暴击率 =  （自身暴击等级 + 500)/（敌方韧性等级 + 500）- 1
	 *
	 * @param attacker
	 * @param defender
	 * @return 
	 */
	public byte calcCrit( BaseFighter attacker, BaseFighter defender ) {
		
		byte crit = 1;//暴击加成
		
		int r = RandomUtil.getRandomInt( 0, 100 );//随机值
		r = 10;//取消随机对测试的影响
		float result = ( attacker.getCrit() + 500 ) / ( defender.getUnCrit() + 500 ) - 1;
		result *= 100f;
		if( result > r ){//发生了暴击
			crit = (byte) RandomUtil.getRandomInt( 2, 4 );//计算实际的暴击范畴2-4
		}
		return crit;
	}

	@Override
	public int normalAttack( BaseFighter attacker, BaseFighter defender ) {
		int r = RandomUtil.getRandomInt( 0, 40 );//伤害波动值，要求在-20~+20之间
		r -= 20;
		r = 0;//取消随机对测试的影响
		float damage = attacker.getPhyAttack() * ( 1f - ( defender.getPhyDefend()/( defender.getPhyDefend() + 10000f ) ) );
		damage *= 1f + r / 100f;
		damage *= ((float)attacker.getLevel() + 10)/ ((float)defender.getLevel() + 10);
		return (int) damage;
	}
	

	
	public static void main(String[] args) {
		float r = 100f / 133;
		System.out.println( r );
		
		r = (float)200 / 30;
		System.out.println( r );
	}
}
