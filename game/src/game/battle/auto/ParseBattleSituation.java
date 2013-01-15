package game.battle.auto;

import game.battle.AttackType;
import game.battle.formation.IFormation;
import game.fighter.BaseFighter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class ParseBattleSituation {

	IFormation 				attackers,defenders;
	//BattleSituation 		situation;
	ByteBuffer 				data;
	
	public ParseBattleSituation( IFormation aFormation, IFormation dFormation, BattleSituation situation ) {
		attackers = aFormation;
		defenders = dFormation;
		//this.situation = situation;
		data = situation.getData();
		data.flip();
	}

	private BaseFighter getFighterByPos( byte pos ){
		for( BaseFighter f : attackers.getAllFighters() ){
			if( f.getPosition() == pos ){
				return f;
			}
		}
		for( BaseFighter f : defenders.getAllFighters() ){
			if( f.getPosition() == pos ){
				return f;
			}
		}
		return null;
	}
	/**
	 * 解析战况信息
	 */
	public void parse( ) {
		List<BaseFighter> all = new ArrayList<BaseFighter>();
		all.addAll( attackers.getAllFighters() );
		all.addAll( defenders.getAllFighters() );
		
//		for( int i = 0; i < 18; i++ ){
//			BaseFighter f = all.get( i );
//			if( f != null ){
//				System.out.println( f.getName() );
//			}
//			else{
//				
//			}
//		}
		int endPosition = data.limit();
		//System.out.println( "字节数:" + endPosition );
		//int i = 0;
		
		String ret = "攻\t防\t命      暴    格\t伤害\t反击\t";
		System.out.println( ret );
		
		while( data.position() < endPosition ){	
			
			AttackType at = AttackType.fromNumber( data.get() );
			switch( at ){
			case BEGIN_ROUND:
				parseRound();
				break;
			case NORMAL_ATTACK:
				parseNormalAttack();
				break;
			case SKILL_ATTACK:
				parseSkillAttack();
				break;
			default:
				break;
			}
		}
	}

	private void parseSkillAttack() {
		byte attackerPos = data.get();
		byte skillId = data.get();
		byte count = data.get();//受到技能影响的战士数量
		for( int i = 0; i < count; i++ ){
			byte defenderPos = data.get();
			byte effectCount = data.get();
			for( int n = 0; n < effectCount; n++ ){
				
			}
		}
		
	}

	private void parseNormalAttack() {
		byte attackerPos = data.get();
		byte defenderPos = data.get();
		AttackInfo info = new AttackInfo( data.get() );
		int damage = 0;
		if( info.isHit() ){
			damage = data.getInt();
		}
		int counterAttackDamage = 0; 
		if( info.isBlock() ){
			counterAttackDamage = data.getInt();
		}
		System.out.println( attackerPos + "\t" + defenderPos + "\t"
				+ info.isHit() + "|" + info.getCrit() + "|" + info.isBlock() + "\t"
				+ damage + "\t" + counterAttackDamage );
		
		
		BaseFighter attacker = getFighterByPos(attackerPos);
		BaseFighter defender = getFighterByPos(defenderPos);		
		if( info.isHit() ){
			attacker.setSp( attacker.getSp() + AutoBattle.SP_TO_ADD );
			if( info.getDamage() > 1 ){//防止不死之身之类的技能长久不结束
				defender.setSp( defender.getSp() + AutoBattle.SP_TO_ADD );
			}
		
			defender.setHp( defender.getHp() - damage );
			if( info.isBlock() ){
				attacker.setHp( attacker.getHp() - counterAttackDamage );
			}
		}		
	}

	private void parseRound() {
		System.out.println( "----------------------------------------------------------------" );
		
	}

}
