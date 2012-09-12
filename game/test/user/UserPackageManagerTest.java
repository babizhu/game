package user;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 包管理器测试
 * @author liukun
 *
 */
public class UserPackageManagerTest {

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
	/**
	 * 直接执行此测试，会成功，但是会抛异常，因为user不存在相应的连接conn
	 * 应该不影响测试
	 */
	public void testRun() {
//		UserPackageManager pm = new UserPackageManager(); 
//		ErrorCode code = pm.run( null, (short) 123, null );
//		assertEquals( ErrorCode.PACKAGE_NOT_FOUND, code );
//		
//		ByteBuffer buf = ByteBuffer.allocate( 4 );
//		buf.putInt( 1 );
//		buf.flip();
//
//		UserInfo user = new UserInfo( null ); 
//		user.setName( "babizhu" );
//		ErrorCode code = pm.run(  user, Packages.USER_LOGIN, buf );
//		assertEquals( code, ErrorCode.SUCCESS );
//		
//		buf.position( 0 );
//		buf.limit( buf.capacity() );
//		buf.putInt( 3 );
//		buf.flip();
//		code = pm.run( user, Packages.USER_EXIT, buf );
//		assertEquals( ErrorCode.SUCCESS, code );
		//
		
		//TODO 修改代码之后，暂时没有测试项
		
	}

}
