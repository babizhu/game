package game.db;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

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
		int count = 100000;////测试次数
		
		long begin = System.nanoTime();
		for( int i = 0; i < count; i++ ){
			Connection con = DatabaseUtil.getConnection();
			PreparedStatement pst = null;
			ResultSet rs = null;
			try {
				String sql = "SELECT * from user_base where name=?";
				pst = con.prepareStatement( sql );
				pst.setString( 1, "刘昆"+new Random().nextInt( 10000 ) );
				rs = pst.executeQuery();
	
				if( rs.next() ) {
					int level = rs.getShort("level") ;
					String nickName = rs.getString("nick_name") ;
					if( level > 2000 ){
						System.out.println( nickName ); 
					}
					
				}
				else{//数据库无此玩家
				}			
			} catch (SQLException e) {
			}
			finally{			
				DatabaseUtil.close( rs, pst, con );
			}
		}
		
		System.out.println( "耗时：" + (System.nanoTime() - begin) / 1000000000f + "秒" );
	}

	
}
