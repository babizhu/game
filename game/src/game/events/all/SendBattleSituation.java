package game.events.all;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import user.UserInfo;
import game.battle.auto.web.Formation9;
import game.battle.auto.web.ParseBattleSituation;
import game.battle.auto.web.WebAutoBattle;
import game.battle.formation.IFormation;

import game.events.EventBase;
import game.fighter.FighterBase;
import game.fighter.cfg.NpcFighterTempletCfg;
import game.mission.cfg.MissionTempletCfg;

/**
 * 和客户端进行通信测试，专门用与测试的，不是正式的程序
 * @author liukun
 * 2013-1-22 下午6:17:37
 */
public class SendBattleSituation extends EventBase {

	IFormation aFormation;
	IFormation dFormation;
	
	void init(){
		List<FighterBase> attackers = new ArrayList<FighterBase>();		
		FighterBase fighter = NpcFighterTempletCfg.getNpcCloneById( (short) 1 );
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
		
		WebAutoBattle battle = new WebAutoBattle( aFormation, dFormation );
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
