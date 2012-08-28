package user;

import game.db.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ErrorCode;
import util.SystemTimer;



/**
 * 和数据库打交道
 * 单体
 * 
 * @author liukun 2012-8-20 下午05:53:59
 */
public class UserInfoDataProvider {
	private final static Logger logger = LoggerFactory.getLogger(UserInfoDataProvider.class);
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
				user.setLevel( rs.getShort("level") );
				user.setNickName( rs.getString("nick_name") );
				System.out.println( user.getNickName() );
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
		String sql = "SELECT sex from user_base where name=?";
		try {
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
		if( nickNameIsDuplicate( user.getName() ) ){
			return ErrorCode.USER_DUPLICATE_NAME;
		}
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;								  
		String sql = "insert into user_base(name,nick_name, sex, money, strength,create_time,is_adult) values" +
										  "(   ?,	     ?,   ?,	 ?,        ?,          ?,       ?)";
		int	i = 1;
		try {
			pst = con.prepareStatement( sql );
			pst.setString( i++, user.getName() );
			pst.setString( i++, user.getNickName() );
			pst.setByte( i++, user.getSex() );
			pst.setInt( i++, user.getMoney() );
			pst.setInt( i++, user.getStrength() );
			pst.setInt( i++, SystemTimer.currentTimeSecond() );
			pst.setByte( i++, (byte) (user.isAdult() ? 1 : 0) );
			pst.execute();
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage() );
			return ErrorCode.DB_ERROR;
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		return ErrorCode.SUCCESS;
	}
}
