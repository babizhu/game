package game.battle.auto.buff;

import game.battle.BuffBase;
import game.battle.BuffRunPoint;
import game.fighter.FighterBase;

/**
 * 头晕目眩
 * 停止1回合行动<br>
 * 运行点：BuffRunPoint.DEFENDING
 * @author liukun
 * 2012-11-1 上午11:01:24
 */
public class B004 extends BuffBase {

	private static final int TURNS = 1;						//持续回合数
	
	public B004( FighterBase fighter ) {
		super( fighter );
		duration = TURNS;
	}

	@Override
	public int run( int damage ) {
		if( duration-- == 0 ){
			stop();
		}
		return damage;
	}

	@Override
	public void start() {
		super.start();
		self.setCanHit( false );
	}
	
	@Override
	protected void stop() {
		super.stop();
		self.setCanHit( true );
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
