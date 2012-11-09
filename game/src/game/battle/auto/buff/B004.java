package game.battle.auto.buff;

import game.battle.BaseBattle;
import game.battle.BaseBuff;
import game.battle.BuffRunPoint;
import game.battle.RunTimeFighter;

/**
 * 眩晕，停止1回合行动<br>
 * 运行点：BuffRunPoint.DEFENDING
 * @author liukun
 * 2012-11-1 上午11:01:24
 */
public class B004 extends BaseBuff {

	private static final int TURNS = 1;						//持续回合数
	private int turn = TURNS;								//本buff已经生效的回合数，递减
	
	public B004( RunTimeFighter rf ) {
		super( rf );
		runTimeFighter.setCanHit( false );
	}

	@Override
	public int run( BaseBattle battle, int damage ) {
		if( turn-- == 0 ){
			end();
		}
		return damage;
	}
	
	private void end() {
		runTimeFighter.setCanHit( true );
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
