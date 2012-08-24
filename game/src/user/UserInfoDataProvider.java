package user;

import game.db.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * 和数据库打交道
 * 单体
 * 
 * @author liukun 2012-8-20 下午05:53:59
 */
public class UserInfoDataProvider {
	
	private static UserInfoDataProvider instance = new UserInfoDataProvider();
	public static  UserInfoDataProvider getInstance(){
		return instance;
	}
	private UserInfoDataProvider(){
	}
	
	/**
	 * 玩家尝试登陆命令
	 * @param user
	 */
	void login(UserInfo user) {
		Connection conn = DatabaseUtil.getConnection();
		PreparedStatement pst;
		try {
			String sql = "SELECT * from user_base where name=?";
			pst = conn.prepareStatement( sql );
			pst.setString( 1, user.getName() );
			ResultSet rs = pst.executeQuery();

			if( rs.next() ) {
				user.setLevel( rs.getByte("level") );
				user.setNickName( rs.getString("nick_name") );
				user.setStatus( UserStatus.fromNum( rs.getByte( "status" ) ) );
				user.setMoney( rs.getInt( "money" ) );
			}
			else{//数据库无此玩家
				user.setStatus( UserStatus.NEW );
			}
			
			rs.close();
			pst.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
