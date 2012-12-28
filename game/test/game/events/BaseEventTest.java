/**
 * 测试BasePackage类
 */
package game.events;

import static org.junit.Assert.*;
import game.events.BaseEvent;
import game.events.EventManager;
import game.events.all.UserLoginEvent;

import java.nio.ByteBuffer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author liukun
 * 2012-8-26
 */
public class BaseEventTest {

	UserLoginEvent event = new UserLoginEvent();//由于BasePackage无法实例化，这里用UserLoginPackage代替
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link game.events.BaseEvent#buildEmptyPackage(int)}.
	 */
	@Test
	public void testBuildEmptyPackage() {
		ByteBuffer buf = event.buildEmptyPackage( 1024 );
		assertEquals( 5, buf.position() );//5 for HEAD(byte) + packageNo(short) + len(short)
		buf.flip();
		
		assertEquals( BaseEvent.HEAD, buf.get() );
		assertEquals( event.getEventId(), buf.getShort() );
		assertEquals( 0, buf.getShort() );

	}

	/**
	 * Test method for {@link game.events.BaseEvent#toString(java.nio.ByteBuffer)}.
	 * 测试toString函数不会影响原ByteBuffer的任何属性
	 */
	@Test
	public void testToStringByteBuffer() {
		ByteBuffer buf = ByteBuffer.allocate( 1024 );
		buf.put( BaseEvent.HEAD );
		buf.putShort( EventManager.USER_CREATE.toNum() );
		buf.putShort( (short) 4 );
		buf.putInt( 88 );
		buf.put( BaseEvent.FOOT );
		
		int limit = buf.limit();
		int pos = buf.position();
		
		System.out.println( event.toString( buf ) );
		assertEquals( limit, buf.limit() );
		assertEquals( pos, buf.position() );
	}
}
