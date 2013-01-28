package game.award;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import user.UserInfo;
import util.ErrorCode;
import util.db.DatabaseUtil;

/**
 * 和数据库打交道
 * 单体
 * 
 * @author liukun 2012-8-20 下午05:53:59
 */
class AwardDataProvider {
	private final static Logger 		logger = LoggerFactory.getLogger(AwardDataProvider.class);
	private static AwardDataProvider 	instance = new AwardDataProvider();
	private static AtomicLong			maxID;
	static{
		maxID = new AtomicLong( DatabaseUtil.getMaxId( "user_award", "id" ) );
	}
	static  AwardDataProvider getInstance(){
		return instance;
	}
	private AwardDataProvider(){
	}
	
	
	/**
	 * 从数据库中获取玩家信息
	 * @param user
	 * @return
	 * 		DB_ERROR,USER_NOT_FOUND
	 */
	ErrorCode get( String uName ) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * from user_award where user_name=?";
			pst = con.prepareStatement( sql );
			pst.setString( 1, uName );
			rs = pst.executeQuery();

			if( rs.next() ) {
				
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
	 * 创建玩家
	 * @param user
	 * @return
	 * 		USER_DUPLICATE_NAME,DB_ERROR
	 */
	ErrorCode create( Award award ) {
		award.setId( maxID.incrementAndGet() );
		
		return null;
		
	}
	
	/**
	 * 修改玩家信息，针对某些改动比较频繁且重要的字段，可考虑专门做一个语法糖，优化的事情放到以后再说
	 * @param user
	 * @return
	 * 		DB_ERROR
	 */
	ErrorCode update(UserInfo user) {
		
	
		return ErrorCode.SUCCESS;
	}
	
}
