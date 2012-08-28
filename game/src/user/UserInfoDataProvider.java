package user;

import game.db.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ErrorCode;



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
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * from user_base where name=?";
			pst = con.prepareStatement( sql );
			pst.setString( 1, user.getName() );
			rs = pst.executeQuery();

			if( rs.next() ) {
				user.setLevel( rs.getByte("level") );
				user.setNickName( rs.getString("nick_name") );
				user.setStatus( UserStatus.fromNum( rs.getByte( "status" ) ) );
				user.setMoney( rs.getInt( "money" ) );
			}
			else{//数据库无此玩家
				user.setStatus( UserStatus.NEW );
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{			
			DatabaseUtil.close( rs, pst, con );
		}
	}
	
	/**
	 * 检测昵称在游戏中是否已经存在
	 * @param uName
	 * @return
	 * 		true:	昵称存在
	 */
	boolean nickNameIsDuplicate( String uName ){
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT sex from user_base where name=?";
			pst = con.prepareStatement( sql );
			pst.setString( 1, uName );
			rs = pst.executeQuery();

			if( rs.next() ) {
				return true;  
			}
			else{//数据库无此玩家
				return false;
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{			
			DatabaseUtil.close( rs, pst, con );
		}
		return true;
	}
	
	/**
	 * 创建玩家
	 * @param user
	 */
	public ErrorCode create(UserInfo user) {
		// TODO Auto-generated method stub
		
	}
}
