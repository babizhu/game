package game.battle.auto;

import game.battle.formation.IFormation;
import game.fighter.BaseFighter;
import game.fighter.cfg.NpcFighterTempletCfg;

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
		NpcFighterTempletCfg.init();
		List<BaseFighter> attackers = new ArrayList<BaseFighter>();		
		BaseFighter fighter = NpcFighterTempletCfg.getCopyById( (short) 1 );
		attackers.add( fighter );
		aFormation = new Formation9( attackers, true, null );
		
		List<BaseFighter> defenders = new ArrayList<BaseFighter>();		
		BaseFighter fighter1 = NpcFighterTempletCfg.getCopyById( (short) 2 );
		defenders.add( fighter1 );
		dFormation = new Formation9( defenders, false, null );
		
	}
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		
		
		init();
		
		AutoBattle battle = new AutoBattle( aFormation, dFormation);
		battle.run();
	}

}
