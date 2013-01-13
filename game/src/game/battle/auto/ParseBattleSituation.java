package game.battle.auto;

import game.battle.AttackType;
import game.battle.formation.IFormation;
import game.fighter.BaseFighter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class ParseBattleSituation {

	IFormation 				attackers,defenders;
	BattleSituation 		situation;
	ByteBuffer 				data;
	
	public ParseBattleSituation(IFormation aFormation, IFormation dFormation, BattleSituation situation ) {
		attackers = aFormation;
		defenders = dFormation;
		this.situation = situation;
		data = this.situation.getData();
		data.flip();
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
				break;
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
	}

	private void parseRound() {
		System.out.println( "----------------------------------------------------------------" );
		
	}

}
