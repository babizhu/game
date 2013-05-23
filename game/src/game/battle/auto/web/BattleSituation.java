package game.battle.auto.web;

import game.battle.AttackType;
import game.fighter.FighterBase;
import game.fighter.FighterAttribute;

import java.nio.ByteBuffer;

public class BattleSituation {
	
	private ByteBuffer	situation = null;
	
	public BattleSituation( int size ){
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
	public void putNormalAttack( FighterBase attacker, FighterBase defender, AttackInfo info ) {
		situation.put( AttackType.NORMAL_ATTACK.toNumber() ).put( attacker.getPosition() ).put( defender.getPosition() );
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

		situation.put( info.getRawData() );
		if( info.isHit() ){
			situation.putInt( info.getDamage() );
		}
	}

	/**
	 * 放置技能攻击的前缀信息
	 * @param attacker
	 * @param skillId
	 */
	public void putSkillAttackPrefix( FighterBase attacker, byte skillId, byte count ) {
		situation.put( AttackType.SKILL_ATTACK.toNumber() ).put( attacker.getPosition() ).put( skillId ).put( count );
	}

	/**
	 * 技能攻击中对敌人的攻击信息
	 * @param attribute
	 * @param defender
	 * @param info
	 */
	public void putSkillInfo( FighterAttribute attribute, AttackInfo info ) {
		situation.put( attribute.toNumber() );
		put( info );
		
	}

	/**
	 * 技能攻击中，除开对敌人攻击的，其他信息的记录，例如加自己的hp，降低对方的sp等信息
	 * @param attribute
	 * @param defender
	 * @param numberToChange
	 */
	public void putSkillInfo(FighterAttribute attribute, int numberToChange) {
		situation.put( attribute.toNumber() ).putInt( numberToChange );
		
	}

	/**
	 * 配合技能攻击，单独放入受技能影响的战士位置
	 * @param position
	 */
	public void putFighter(byte position) {
		situation.put(position);		
	}

	/**
	 * 配合技能攻击，单独放入战士受影响的属性个数
	 * @param effectCount
	 */
	public void putEffectCount( byte effectCount ) {
		situation.put( effectCount );
	}

	public ByteBuffer getData() {
		return situation;
	}	
}
