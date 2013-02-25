/**
 * 
 */
package game.events;


import static org.junit.Assert.assertEquals;
import game.events.Event;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author liukun
 * 2012-8-26
 */
public class EventTest {

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
	 * 测试不存在的包号
	 */
	@Test
	public void testEvents(){
		short errNumber = 1;
		Event pack = Event.fromNum( errNumber );
		assertEquals( null, pack );
	}

}
