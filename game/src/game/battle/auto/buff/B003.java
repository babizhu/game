package game.battle.auto.buff;

import game.battle.BaseBattle;
import game.battle.BaseBuff;
import game.battle.BuffRunPoint;
import game.battle.RunTimeFighter;

/**
 * 中毒效果
 * 发招前，自身掉血500（技能中计算，并传过来）点，持续3个回合<br>
 * 运行点：BuffRunPoint.BEFORE_ATTACK
 * @author liukun
 * 2012-11-1 上午11:01:24
 */
public class B003 extends BaseBuff {

	private static final int TURNS = 3;						//持续回合数
	private int turn = TURNS;								//本buff已经生效的回合数，递减
	/**
	 * 要减的hp
	 */
	private final int skillDamage;
	
	public B003( RunTimeFighter rFighter, int damage ) {
		super( rFighter );
		this.skillDamage = damage;
	}

	@Override
	public int run( BaseBattle battle, int damage ) {
		if( turn-- > 0 ){
			//TODO 中毒伤害的效果到战况信息中
			return skillDamage;
		}
		else{
			end();
			return 0;
		}
	}

	private void end() {
		this.isRemove = true;
	}

	@Override
	public BuffRunPoint getBuffRunPoint() {
		return BuffRunPoint.BEFORE_ATTACK;
	}
	public static void main(String[] args) {
		int turn = 2;
		while( turn-- > 0 ){
			System.out.println( turn );
		}
	
	}
	
}
