package user;

import game.db.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 和数据库打交道 单体
 * 
 * @author liukun 2012-8-20 下午05:53:59
 */
public class UserInfoDataProvider {
	
	/**
	 * 玩家尝试登陆命令
	 * @param user
	 */
	void login(UserInfo user) {
		Connection conn = DatabaseUtil.getConnection();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT rank,uname from city_elite order by rank");

			while (rs != null && rs.next()) {
				int rank = rs.getInt("rank");
				String uname = rs.getString("uname");
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
