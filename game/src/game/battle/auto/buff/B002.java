package game.battle.auto.buff;

import game.battle.BaseBattle;
import game.battle.BaseBuff;
import game.battle.BuffRunPoint;
import game.battle.RunTimeFighter;
import game.fighter.BaseFighter;


/**
 * 增加30%的物理攻击，持续2回合<br>
 * 运行点：BuffRunPoint.NOW
 * @author liukun
 * 2012-11-1 上午11:01:24
 */
public class B002 extends BaseBuff {

	private static final int 	TURNS = 2;											//持续回合数
	private int 				turn = TURNS;										//本buff已经生效的回合数
	private final BaseFighter	fighter;
	private final int 			addAttack;
	
	public B002( RunTimeFighter rFighter) {
		super( rFighter );
		fighter = rFighter.getFighter();
		addAttack = (int) (fighter.getPhyAttack() * 0.3f);
	}

	@Override
	public int run( BaseBattle battle, int damage ) {
		if( turn-- == 0 ){			
			end();
		}
		return damage;		
	}

	@Override
	public void init() {
		fighter.setPhyAttack( fighter.getPhyAttack() + addAttack );
		
	}

	private void end() {
		this.isRemove = true;
		fighter.setPhyAttack( fighter.getPhyAttack() - addAttack );//还原
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
