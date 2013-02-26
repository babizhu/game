package game.battle.auto.buff;

import game.battle.BuffBase;
import game.battle.BuffRunPoint;
import game.fighter.FighterBase;

/**
 * 病入膏肓
 * 发招前，自身掉血500（技能中计算，并传过来）点，持续3个回合<br>
 * 运行点：BuffRunPoint.BEFORE_ATTACK
 * @author liukun
 * 2012-11-1 上午11:01:24
 */
public class B003 extends BuffBase {

	private static final int TURNS = 3;						//持续回合数
	
	/**
	 * 要减的hp
	 */
	private final int skillDamage;
	
	public B003( FighterBase fighter, int damage ) {
		super( fighter );
		this.skillDamage = damage;
		duration = TURNS;
	}

	@Override
	public int run( int damage ) {
		if( duration-- > 0 ){
			//TODO 中毒伤害的效果到战况信息中
			return skillDamage;
		}
		else{
			stop();
			return 0;
		}
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
