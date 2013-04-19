package util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DatabaseUtil {

	/**
	 * 配置文件的路径
	 */
	//private static final String			FILE	= "config/db.xml";

	
	private final static Logger 		logger = LoggerFactory.getLogger( DatabaseUtil.class );
	private static DataSource	 		dataSource;
	
	static{
		init();
	}
	/**
	 *	从连接池中获取一个有效连接 
	 * @return
	 * 		返回一个可用数据库连接
	 */
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	
	/**
	 * 从配置表中读取相应的连接参数，初始化数据库连接池
	 */
	public static void init () {
		try {
			dataSource = DruidDataSourceFactory.createDataSource( MySqlConfigProperty.getInstance().getProperties() );
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 关闭本次查询所有的打开的资源，除了rs，其余两个资源应该不可能为null，有待考证
	 * @param rs
	 * @param st
	 * @param con
	 */
	public static void close( ResultSet rs, Statement st, Connection con ){
		try {
			if( rs != null ){
				rs.close();
			}
			if( st != null ){
				st.close();
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据库某张表的最大id
	 * @param tableName
	 * @param col
	 * @return
	 */
	public static long getMaxId( String tableName, String col ){
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sql = "select max(" + col + ") from " + tableName;
		
		try {
			pst = con.prepareStatement( sql );
			rs = pst.executeQuery();
			
			if (rs.next()) {
				return rs.getLong( 1 );
			}
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		return 0;
	}

	public static void main ( String[] args ) {
		Connection con = DatabaseUtil.getConnection();
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sql = "select count(*) from user_base";
		
		try {
			pst = con.prepareStatement( sql );
			rs = pst.executeQuery();
			
			if (rs.next()) {
				System.out.println( rs.getLong( 1 ) );
			}
		} catch (SQLException e) {
			//logger.debug( e.getLocalizedMessage(), e );
			System.out.println( e );
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
	}

}
