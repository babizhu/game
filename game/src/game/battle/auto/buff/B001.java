package game.battle.auto.buff;

import game.BuffRunPoint;
import game.battle.BaseBattle;
import game.battle.BaseBuff;
import game.fighter.BaseFighter;


/**
 * 不死之身，被攻击后，伤害值为1
 * @author admin
 * 2012-11-1 上午11:01:24
 */
public class B001 extends BaseBuff {

	private static final int TURNS = 2;						//持续回合数
	private int turn = TURNS;								//本buff已经生效的回合数
	
	public B001(BaseBattle battle, BaseFighter fighter) {
		super(battle, fighter);
		point = BuffRunPoint.DEFENDING;
		// TODO Auto-generated constructor stub
	}

	@Override
	public float run( float damage ) {
		
		return 1;
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void begin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void end() {
		this.isRemove = true;
	}

	
}
