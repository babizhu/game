package game.battle.auto.buff;

import game.battle.BuffBase;
import game.battle.BuffRunPoint;
import game.fighter.FighterBase;


/**
 * 不死之身<br>
 * 被攻击后，伤害值降低为1，持续2回合<br>
 * 运行点：BuffRunPoint.AFTER_DEFENDING
 * @author liukun
 * 2012-11-1 上午11:01:24
 */
public class B001 extends BuffBase {

	private static final int 	TURNS = 2;									//持续回合数
	
	public B001( FighterBase fighter ) {
		super( fighter );
		duration = TURNS;
	}

	@Override
	public int run( int damage ) {
		if( duration-- > 0 ){
			return 1;
		}
		else{
			stop();
			return damage;
		}		
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
