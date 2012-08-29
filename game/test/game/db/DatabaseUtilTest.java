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
	 * 测试从缓冲池获取的连接是否为null
	 */
	@Test
	public void testGetConnection () {
		Connection conn = DatabaseUtil.getConnection();
		assertNotNull( conn );
	}
	

	@Test
	/**
	 * 测试数据库的连接池
	 */
	public void testPerfomance() throws SQLException{
		int count = 10000;////测试次数
		
		long begin = System.nanoTime();
		for( int i = 0; i < count; i++ ){
			Connection conn = DatabaseUtil.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * from user_base" );
			
			while( rs.next()){
				
				//int rank = rs.getInt( "money" );
				//String uname = rs.getString( "nick_name" );				
			}
			DatabaseUtil.close(rs, stmt, conn);
		}
		
		System.out.println( "耗时：" + (System.nanoTime() - begin) / 1000000000f + "秒" );
	}

	
}
