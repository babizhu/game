package game.battle.auto;

import game.battle.AttackType;
import game.fighter.BaseFighter;

import java.nio.ByteBuffer;

public class WarSituation {
	
	private ByteBuffer	situation = null;
	
	public WarSituation( int size ){
		situation = ByteBuffer.allocate( size );
	}
	
	/**
	 * 回合分隔符，标识一个回合开始
	 */
	public void putRoundFlag( ){
		situation.put( AttackType.BEGIN_ROUND.toNumber() );
	}

	/**
	 * 一次普通攻击的情况
	 * @param attacker
	 * @param defender
	 * @param info
	 */
	public void put(BaseFighter attacker, BaseFighter defender, AttackInfo info) {
		situation.put( AttackType.NORMAL_ATTACK.toNumber()).put( attacker.getPosition() ).put( defender.getPosition() );
		put( info );
		
	}

	/**
	 * 放置反击信息
	 * @param damage
	 */
	public void putCounterAttackDamage( int damage ) {
		situation.putInt( damage );
		
	}
	
	private void put(AttackInfo info) {
		situation.putInt( info.getDamage() );
		situation.put( info.getRawData() );		
	}
	
}
