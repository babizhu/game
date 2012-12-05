package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RandomUtilTest {

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

	//private void 
	@Test
	public void testGetRandomInt() {
		Map<Integer,Integer> result = new HashMap<Integer, Integer>();
		
		for( int i = 0; i < 100; i++ ){
			int n = RandomUtil.getRandomInt( 1, 5 );
			if( !result.containsKey( n ) ){
				result.put( n, 1 );
			}
			else{
				int count = result.get( n );
				count++;
				result.put( n , count );
			}
		}
		for( Entry<Integer, Integer> e : result.entrySet() ){
			System.out.println( e.getKey() + "\t\t" + e.getValue() );
		}
	}

}
