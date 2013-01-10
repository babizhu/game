package game.battle.auto;

import game.battle.formation.IFormation;
import game.fighter.BaseFighter;
import game.fighter.cfg.NpcFighterTempletCfg;
import game.mission.cfg.MissionTempletCfg;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AutoBattleTest {

	IFormation aFormation;
	IFormation dFormation;
	
	void init(){
		List<BaseFighter> attackers = new ArrayList<BaseFighter>();		
		BaseFighter fighter = NpcFighterTempletCfg.getNpcCloneById( (short) 1 );
		attackers.add( fighter );
		aFormation = new Formation9( attackers, true, null );
//		
//		List<BaseFighter> defenders = new ArrayList<BaseFighter>();		
//		BaseFighter fighter1 = NpcFighterTempletCfg.getCopyById( (short) 2 );
//
//		fighter1.setPosition( (byte) 2 );
//		defenders.add( fighter1 );
//		dFormation = new Formation9( defenders, false, null );
		short missionId = 1;
		//aFormation = MissionTempletCfg.getTempletById( missionId ).getFormationClone( 0 );
		dFormation = MissionTempletCfg.getTempletById( missionId ).getFormationClone( 0 );
		
	}
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MissionTempletCfg.init();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRun() {
		
		int count = 2;//循环次数
		long begin = System.nanoTime();
		AutoBattle battle= null;
		for( int i = 0; i < count; i++ ){
			init();
		
			battle = new AutoBattle( aFormation, dFormation );
			battle.run();
		}
		
		System.out.println("总共用时" + (System.nanoTime() - begin) / 1000000000f + "秒");
		init();		
		new ParseBattleSituation( aFormation, dFormation, battle.getBattleSituation() ).parse();
	}

}
