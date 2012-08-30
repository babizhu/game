package game.packages;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import user.UserInfo;
import util.ErrorCode;

/**
 * 包管理器测试
 * @author liukun
 *
 */
public class PackageManagerTest {

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
		PackageManager pm = new PackageManager(); 
//		ErrorCode code = pm.run( null, (short) 123, null );
//		assertEquals( ErrorCode.PACKAGE_NOT_FOUND, code );
//		
		ByteBuffer buf = ByteBuffer.allocate( 4 );
		buf.putInt( 1 );
		buf.flip();

		UserInfo user = new UserInfo(); 
		user.setName( "babizhu" );
		ErrorCode code = pm.run(  user, Packages.USER_LOGIN, buf );
		assertEquals( code, ErrorCode.SUCCESS );
		
		buf.position( 0 );
		buf.limit( buf.capacity() );
		buf.putInt( 3 );
		buf.flip();
		code = pm.run( user, Packages.USER_EXIT, buf );
		assertEquals( ErrorCode.SUCCESS, code );
		
	}

}
