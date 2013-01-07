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
	}

	/**
	 * 解析战况信息
	 */
	public void parse(IFormation aFormation, IFormation dFormation) {
		data.flip();
		List<BaseFighter> all = new ArrayList<BaseFighter>();
		all.addAll( aFormation.getAllFighters() );
		all.addAll( dFormation.getAllFighters() );
		
//		for( int i = 0; i < 18; i++ ){
//			BaseFighter f = all.get( i );
//			if( f != null ){
//				System.out.println( f.getName() );
//			}
//			else{
//				
//			}
//		}
		int limit = data.limit();
		while( data.position() != limit ){
			if( data.get() == AttackType.BEGIN_ROUND.toNumber() ){
				continue;
			}
			AttackType at = AttackType.fromNumber( data.get() );
			switch( at ){
			case NORMAL_ATTACK:
				break;
			case SKILL_ATTACK:
				break;
			}
		}
}
