package game.battle.auto;


import game.battle.BuffRunPoint;
import game.battle.IBattleUtil;
import game.battle.formula.Formula;
import game.fighter.BaseFighter;

import java.util.Comparator;

import util.RandomUtil;

public class AutoBattleUtil implements IBattleUtil {

	private static final float 			BLOCK_DAMAGE_RATE = 0.5f;
	
	private static final IBattleUtil 	INSTANCE = new AutoBattleUtil();
	private AutoBattleUtil() {}
	public static final IBattleUtil getInstance(){
		return INSTANCE;
	}
	
	/**
	 * 出手顺序由速度确定
	 */
	private static final Comparator<BaseFighter> speedComparator = new Comparator<BaseFighter>(){
		@Override
		public int compare( BaseFighter f1, BaseFighter f2 ) {
			return f2.getSpeed() - f1.getSpeed();
		}
	};
	
	/**
	 * 是否格挡并反击
	 * @param attacker
	 * @param defender
	 * @return
	 */
	private boolean isBlockAndCounterAttack( BaseFighter attacker, BaseFighter defender ) {
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
	private boolean isHit( BaseFighter attacker, BaseFighter defender ) {
		int r = RandomUtil.getRandomInt( 0, 100 );//随机值
		r = 50;//取消随机对测试的影响
		float result = (float)(attacker.getHitRate() + 500) / (defender.getDodgeRate() + 500);
		result *= 100;
		return result > r;
	}
	
	/**
	 *	暴击率 =  （自身暴击等级 + 500)/（敌方韧性等级 + 500）- 1
	 *
	 * @param attacker
	 * @param defender
	 * @return 
	 */
	private byte calcCrit( BaseFighter attacker, BaseFighter defender ) {
		
		byte crit = 1;//暴击加成
		
		int r = RandomUtil.getRandomInt( 0, 100 );//随机值
		r = 10;//取消随机对测试的影响
		float result = (float)( attacker.getCrit() + 500 ) / ( defender.getUnCrit() + 500 ) - 1;
		result *= 100f;
		if( result > r ){//发生了暴击
			crit = (byte) RandomUtil.getRandomInt( 2, 4 );//计算实际的暴击范畴2-4
		}
		return crit;
	}

	/**
	 * 根据公式计算普通攻击的伤害值
	 * @param attacker
	 * @param defender
	 * @param formula	计算公式
	 * @param arguments	相应参数，如不存在，请放入null
	 * @return
	 */
	@Override
	public AttackInfo calcAttackInfo( BaseFighter attacker, BaseFighter defender, Formula formula, float[] arguments ) {
		
		AttackInfo info = new AttackInfo();
		boolean isHit = isHit(attacker, defender);
		if( !isHit ){
			return info;
		}
		
		int crit  = calcCrit(attacker, defender);
		boolean isBlock = this.isBlockAndCounterAttack(attacker, defender);
		
		int damage = formula.run( attacker, defender, arguments );
		damage *= crit;
		if( isBlock ){
			damage *= BLOCK_DAMAGE_RATE;
		}
		info.setCrit(crit);
		info.SetBlock( isBlock );
		info.SetHit( isHit );
		
		damage = defender.getBm().run( damage, BuffRunPoint.AFTER_DEFENDING );
		info.setDamage( damage );

		return info;
	}
	public static void main(String[] args) {
		float r = 100f / 133;
		System.out.println( r );
		
		r = (float)200 / 30;
		System.out.println( r );
		
		int damage = 301;
		damage *= BLOCK_DAMAGE_RATE;
		System.out.println( damage);
	}

	@Override
	public int calcCounterAttackDamage(BaseFighter attacker, BaseFighter defender) {
		int damage = Formula.NormalAttackFormula.run( attacker, defender, null );

		return (int) (damage * BLOCK_DAMAGE_RATE);
	}
	@Override
	public Comparator<BaseFighter> getOrderComparator() {
		return speedComparator;
	}
	
}
