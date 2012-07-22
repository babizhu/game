package game.db;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.druid.pool.DruidDataSource;

public class DatabaseUtil {

	
	private DatabaseUtil() {
	}

	private static DatabaseUtil	instance	= new DatabaseUtil();
	private DruidDataSource dataSource		= new DruidDataSource();
	
	public static DatabaseUtil getInstance () {
		return instance;
	}
	
	/**
	 * �����ӳػ�ȡһ�����õ�����
	 * @return
	 * 		����һ���������ݿ�����
	 */
	public Connection getConnection(){
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	/**
	 * �����ļ���·��
	 */
	private static String	filePath	= "config/db.xml";

	/**
	 * �����ñ��ж�ȡ��Ӧ�����Ӳ�������ʼ�����ݿ�����
	 */
	public void init () {
		String 	user = null;
		String	password = null;
		String	url = null;
		try {

			File file = new File( filePath );
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

	public static void main ( String[] args ) {
		
	}

}
