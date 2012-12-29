package game.battle.auto.buff;

import game.battle.BaseBuff;
import game.battle.BuffRunPoint;
import game.fighter.BaseFighter;


/**
 * 无坚不摧
 * 增加30%的物理攻击，持续2回合<br>
 * 运行点：BuffRunPoint.NOW
 * @author liukun
 * 2012-11-1 上午11:01:24
 */
public class B002 extends BaseBuff {

	private static final int 	TURNS = 2;											//持续回合数
	private final int 			addAttack;
	
	public B002( BaseFighter fighter) {
		super( fighter );
		addAttack = (int) (fighter.getPhyAttack() * 0.3f);
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
		self.setPhyAttack( self.getPhyAttack() + addAttack );
	}

	@Override
	protected void stop() {
		super.stop();
		self.setPhyAttack( self.getPhyAttack() - addAttack );//还原
	}

	@Override
	public BuffRunPoint getBuffRunPoint() {
		return BuffRunPoint.NOW;
	}
	
	
	public static void main(String[] args) {
		int turn = 2;
		while( turn-- > 0 ){
			System.out.println( turn );
		}	
	}
	
}