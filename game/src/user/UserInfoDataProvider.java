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
	static  UserInfoDataProvider getInstance(){
		return instance;
	}
	private UserInfoDataProvider(){
	}
	
	/**
	 * 玩家尝试登陆命令
	 * @param user
	 * @return
	 * 		DB_ERROR,USER_NOT_FOUND
	 */
	ErrorCode get( UserInfo user ) {
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
				user.setStatus( UserStatus.fromNum( rs.getByte( "status" ) ) );
				user.setMoney( rs.getInt( "money" ) );
				user.setStrength( rs.getShort( "strength" ) );
				user.setAdult( rs.getBoolean( "is_adult" ) ) ;
				user.setCreateTime( rs.getInt( "create_time" ) );
				user.setLastLogoutTime( rs.getInt( "lastlogout_time" ) );
				user.setLoginCount( rs.getShort( "login_count" ) );
				user.setSex( rs.getByte( "sex" ) );
				
			}
			else{//数据库无此玩家
				return ErrorCode.USER_NOT_FOUND;
			}			
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
			return ErrorCode.DB_ERROR; 
		}
		finally{			
			DatabaseUtil.close( rs, pst, con );
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 检测昵称或用户名在游戏中是否已经存在
	 * @param uName
	 * @return
	 * 		true:	昵称或者用户名已存在
	 */
	boolean nameIsDuplicate( UserInfo user ){
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "SELECT sex from user_base where name=? or nick_name=?";
		try {
			pst = con.prepareStatement( sql );
			pst.setString( 1, user.getName() );
			pst.setString( 2, user.getNickName() );
			rs = pst.executeQuery();

			if( rs.next() ) {
				return true;  
			}
			else{//数据库无此玩家
				return false;
			}	
			
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
		}
		finally{			
			DatabaseUtil.close( rs, pst, con );
		}
		return true;
	}
	
	/**
	 * 创建玩家
	 * @param user
	 * @return
	 * 		USER_DUPLICATE_NAME,DB_ERROR
	 */
	ErrorCode create(UserInfo user) {
		if( nameIsDuplicate( user ) ){
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
			pst.setBoolean( i++, user.isAdult() );
			pst.executeUpdate();
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
			return ErrorCode.DB_ERROR;
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 修改玩家信息，针对某些改动比较频繁且重要的字段，可考虑专门做一个语法糖，优化的事情放到以后再说
	 * @param user
	 * @return
	 * 		DB_ERROR
	 */
	ErrorCode update(UserInfo user) {
		
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;								  
		String sql = "update user_base set " +
				"money = ?," +
				"strength=?," +
				"level=?," +
				"status=?," +
				"lastlogout_time=?," +
				"is_adult=? " +
				"where name=?";
		int	i = 1;
		try {
			pst = con.prepareStatement( sql );
			pst.setInt( i++, user.getMoney() );
			pst.setShort( i++, user.getStrength() );
			pst.setShort( i++, user.getLevel() );
			pst.setByte( i++, user.getStatus().toNum() );
			pst.setInt( i++, user.getLastLogoutTime() );
			pst.setBoolean( i++, user.isAdult() );
			pst.setString( i++, user.getName() );
			pst.executeUpdate();
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
			return ErrorCode.DB_ERROR;
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		return ErrorCode.SUCCESS;
	}
}
