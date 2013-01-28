package game.events.all.battle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import user.UserInfo;
import game.battle.auto.AutoBattle;
import game.battle.auto.Formation9;
import game.battle.auto.ParseBattleSituation;
import game.battle.formation.IFormation;
import game.events.BaseEvent;
import game.fighter.BaseFighter;
import game.fighter.cfg.NpcFighterTempletCfg;
import game.mission.cfg.MissionTempletCfg;

/**
 * 和客户端进行通信测试
 * @author liukun
 * 2013-1-22 下午6:17:37
 */
public class SendBattleSituation extends BaseEvent {

	IFormation aFormation;
	IFormation dFormation;
	
	void init(){
		List<BaseFighter> attackers = new ArrayList<BaseFighter>();		
		BaseFighter fighter = NpcFighterTempletCfg.getNpcCloneById( (short) 1 );
		attackers.add( fighter );
		fighter = NpcFighterTempletCfg.getNpcCloneById( (short) 2 );
		fighter.setPosition( (byte) 3 );
		attackers.add( fighter );
		aFormation = new Formation9( attackers, true, null );
//	
		short missionId = 2;
		//aFormation = MissionTempletCfg.getTempletById( missionId ).getFormationClone( 0 );
		dFormation = MissionTempletCfg.getTempletById( missionId ).getFormationCloneByWave( 0 );

	}
	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		
		init();
		
		AutoBattle battle = new AutoBattle( aFormation, dFormation );
		battle.run();
		ByteBuffer buffer = buildEmptyPackage( 2048 );
		ByteBuffer content = battle.getBattleSituation().getData().asReadOnlyBuffer();
		content.flip();
		buffer.put( content );
		System.out.println( "战况内容的长度为：" + content.position() );
		sendPackage( user.getCon(), buffer );
		init();		
		new ParseBattleSituation( aFormation, dFormation, battle.getBattleSituation() ).parse();
	}
	
	

}