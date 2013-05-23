package experiment.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.db.DatabaseUtil;

/**
 * 测试数据库，中文编码的问题
 * @author Administrator
 * 2013-5-21 上午11:09:53
 */


public class Encode {

	private static void getAll(){
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * from user_base";
			pst = con.prepareStatement( sql );
			rs = pst.executeQuery();

			while( rs.next() ) {
				
				System.out.println( rs.getString("nick_name") );
				System.out.println( rs.getString("name") );
				System.out.println( "-------------------------------------------------------");
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{			
			DatabaseUtil.close( rs, pst, con );
		}

	}
	
	private static void getOne( String name ){
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * from user_base where name=?";
			pst = con.prepareStatement( sql );
			pst.setString( 1, name );
			rs = pst.executeQuery();
			
			if( rs.next() ) {
				
				System.out.println( rs.getString("nick_name") );
				System.out.println( rs.getString("name") );
				System.out.println( "-------------------------------------------------------");
			}
			else{
				System.out.println( name + "未找到");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{			
			DatabaseUtil.close( rs, pst, con );
		}

	}

	public static void main(String[] args) {
//		getAll();
		getOne( "刘昆0" );
	}

}
