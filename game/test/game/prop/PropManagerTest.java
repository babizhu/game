package game.prop;

import static org.junit.Assert.assertEquals;
import game.prop.cfg.PropTempletCfg;
import game.prop.equipment.Equipment;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import user.UserInfo;
import user.UserManager;
import util.ErrorCode;

public class PropManagerTest {

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropTempletCfg.init();
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

	/**
	 * 测试从数据库装载初始化玩家的道具情况
	 */
	@Test
	public void testPropManager() {
		String name = "bbz";
		UserInfo user = UserManager.getInstance().getUserByName(name );
		PropManager pm = new PropManager(user);
		assertEquals( 25, pm.getFreeGridCount() );
		
		Equipment e = pm.getEquipmentById( 4 );
		assertEquals( 10, e.getLevel() );
		assertEquals( 18000, e.getTemplet().getTempletId() );
		
	}

	/**
	 * 记得执行完之后去调用PropDataProvider.main()函数从数据库中删除数据
	 */
	@Test
	public void testAdd() {
		String name = "刘昆0";
		UserInfo user = UserManager.getInstance().getUserByName(name );
		PropManager pm = new PropManager(user);
		
		short templetId = 10000;
		short count = 101;
		PropUnit unit = new PropUnit( templetId, count );
		
		pm.add( unit );
		assertEquals( 39, pm.getFreeGridCount() );
		/************************第一次添加101个材料，此材料的叠加上限为10**************************/
		
		unit = new PropUnit( (short) 18000, 4 );
		pm.add( unit );
		assertEquals( 35, pm.getFreeGridCount() );
		/************************第二次添加4个装备*************************/
		
		unit = new PropUnit( (short) 19000, 40 );
		ErrorCode code = pm.add( unit );
		assertEquals( ErrorCode.BAG_IS_FULL, code );
		assertEquals( 35, pm.getFreeGridCount() );
		/************************第三次添加40个装备，已经超限5个，添加不成功*************************/
		
		
		unit = new PropUnit( templetId, 4 );		
		pm.add( unit );
		assertEquals( 35, pm.getFreeGridCount() );
		/************************第四次添加4个材料，此材料背包已经存在，不会额外占用格子*************************/
		
		templetId = 10001;
		unit = new PropUnit( templetId, 52 );		
		pm.add( unit );
		assertEquals( 29, pm.getFreeGridCount() );
		/************************第五次添加52个材料，此材料背包不存在，额外占用6格子*************************/

		unit = new PropUnit( (short) 18000, 4 );		
		pm.add( unit );
		assertEquals( 25, pm.getFreeGridCount() );
		/************************第六次添加4个道具，此道具背包已经存在，仍然要额外占用格子*************************/
		
		unit = new PropUnit( (short) 10001, 4000 );
		code = pm.add( unit );
		assertEquals( ErrorCode.BAG_IS_FULL, code );
		assertEquals( 25, pm.getFreeGridCount() );
		/************************第七次添加4000个材料，已经超限，添加不成功*************************/
		
	}
	

	@Test
	public void testGetEquipmentById() {
		//fail("Not yet implemented");
	}

}
