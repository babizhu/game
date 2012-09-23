package user;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserManagerTest {

	
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
	public void testGetInstance() {
		assertNotNull( UserManager.getInstance() );
	}

	@Test
	public void testExit() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserByName() {
		String name = "刘昆1";//真实存在的用户名
		UserInfo user = UserManager.getInstance().getUserByName( name );
		assertEquals( "巴比猪1", user.getNickName() );
		
		name = "不得不离去";//不存在的昵称
		user = UserManager.getInstance().getUserByName( name );
		assertEquals( null, user );
		
		name = null;//null
		user = UserManager.getInstance().getUserByName( name );
		assertEquals( null, user );
		
	}

	@Test
	public void testGetUserByNickName() {
		String nickName = null;
		UserInfo user = UserManager.getInstance().getUserByNickName(nickName);
		assertEquals( user, null );
		
		nickName = "";//空白昵称
		user = UserManager.getInstance().getUserByNickName(nickName);
		assertEquals( null, user );		
		
		nickName = "巴比猪1";//真实存在的昵称
		user = UserManager.getInstance().getUserByNickName(nickName);
		assertEquals( "刘昆1", user.getName() );
		
		nickName = "不得不离去";//不存在的昵称
		user = UserManager.getInstance().getUserByNickName(nickName);
		assertEquals( null, user );
		
	}

	@Test
	public void testRun() {
		fail("Not yet implemented");
	}

	@Test
	public void testLogin() {
		fail("Not yet implemented");
	}


}
