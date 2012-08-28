package game.db;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.druid.pool.DruidDataSource;

public class DatabaseUtil {

	/**
	 * 配置文件的路径
	 */
	private static final String	FILE	= "config/db.xml";

	

	private static DruidDataSource dataSource		= new DruidDataSource();
	
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
		String 	user = null;
		String	password = null;
		String	url = null;
		try {

			File file = new File( FILE );
			SAXReader saxReader = new SAXReader();

			Document doc = saxReader.read( file );
			Element elm = null;

			List<?> projects = doc.selectNodes( "/game" );
			Iterator<?> it = projects.iterator();

			while (it.hasNext()) {
				elm = (Element) it.next();
				user = elm.elementText( "user" );
				password = elm.elementText( "password" );
				url = elm.elementText( "url" );

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		dataSource.setUrl( url );
		dataSource.setUsername( user );
		dataSource.setPassword( password );

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
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main ( String[] args ) {
		
	}

}
