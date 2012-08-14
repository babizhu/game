package game.packages;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import user.UserInfo;
import util.ErrorCode;

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
	public void testRun() {
		PackageManager instance = PackageManager.getInstance(); 
		ErrorCode code = instance.run( (short) 123, null, null );
		assertEquals( code, ErrorCode.PACAKAGE_NOT_FOUND );
		
		ByteBuffer buf = ByteBuffer.allocate( 4 );
		buf.putInt( 1 );
		buf.flip();

		UserInfo user = new UserInfo(); 
		code = instance.run( (short) 1, user, buf );
		assertEquals( code, ErrorCode.SUCCESS );
		
		buf.flip();
		buf.putInt( 3 );
		buf.flip();
		code = instance.run( (short) 1, user, buf );
		assertEquals( code, ErrorCode.SUCCESS );
		
	}

}
