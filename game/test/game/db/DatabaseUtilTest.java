package game.db;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseUtilTest {

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		DatabaseUtil.getInstance().init();
	}

	@AfterClass
	public static void tearDownAfterClass () throws Exception {
	}

	@Before
	public void setUp () throws Exception {
	}

	@After
	public void tearDown () throws Exception {
	}

	/**
	 * ���Դӻ���ػ�ȡ�������Ƿ�Ϊnull
	 */
	@Test
	public void testGetConnection () {
		Connection conn = DatabaseUtil.getInstance().getConnection();
		assertNotNull( conn );
	}
	
	@SuppressWarnings("unused")
	@Test
	/**
	 * �������ݿ�����ӳ�
	 */
	public void testPerfomance() throws SQLException{
		int count = 100000;//���Դ���
		
		long begin = System.nanoTime();
		for( int i = 0; i < count; i++ ){
			Connection conn = DatabaseUtil.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT rank,uname from city_elite order by rank" );
			
			while(rs != null && rs.next()){
				
				int rank = rs.getInt( "rank" );
				String uname = rs.getString( "uname" );				
			}
			rs.close();
			stmt.close();
			conn.close();
		}
		
		System.out.println( "��ʱ��" + (System.nanoTime() - begin) / 1000000000f + "��" );
	}

	
}
