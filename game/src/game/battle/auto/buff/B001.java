package game.battle.auto.buff;

import game.battle.BaseBattle;
import game.battle.BaseBuff;
import game.battle.BuffRunPoint;
import game.battle.RunTimeFighter;


/**
 * 不死之身，被攻击后，伤害值为1，持续2回合<br>
 * 运行点：BuffRunPoint.AFTER_DEFENDING
 * @author liukun
 * 2012-11-1 上午11:01:24
 */
public class B001 extends BaseBuff {

	private static final int 	TURNS = 2;									//持续回合数
	private int 				turn = TURNS;								
	
	public B001( RunTimeFighter rFighter) {
		super( rFighter );
	}

	@Override
	public int run( BaseBattle battle, int damage ) {
		if( turn-- > 0 ){
			return 1;
		}
		else{
			end();
			return damage;
		}		
	}

	private void end() {
		this.isRemove = true;
	}

	@Override
	public BuffRunPoint getBuffRunPoint() {
		return BuffRunPoint.AFTER_DEFENDING;
	}
	public static void main(String[] args) {
		int turn = 2;
		while( turn-- > 0 ){
			System.out.println( turn );
		}
	
	}
	
}
