package game.task;

import game.task.cfg.TaskTempletCfg;
import game.task.enums.TaskStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ErrorCode;
import util.db.DatabaseUtil;

/**
 * 任务系统相关的数据库方法
 * 单体
 * 
 * @author liukun 2012-9-24 12:33:59
 */
class TaskDataProvider {
	private final static Logger logger = LoggerFactory.getLogger(TaskDataProvider.class);
	private static TaskDataProvider instance = new TaskDataProvider();
	static  TaskDataProvider getInstance(){
		return instance;
	}
	private TaskDataProvider(){
	}
	
	/**
	 * 从数据库中获取玩家尚未最终完成（未领奖）的任务信息
	 * @param user
	 * @return
	 * 		DB_ERROR,USER_NOT_FOUND
	 */
	ConcurrentHashMap<Short,BaseTask> getActiveTasksByUser( String uname ) {
		
		ConcurrentHashMap<Short,BaseTask> map = new ConcurrentHashMap<Short, BaseTask>();
		
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT templet_id,accept_sec,done_sec,accept_award_sec,parm,status " +
					"from task_base " +
					"where status <>" + TaskStatus.FINISH.toNum() + " and uname=?";
			
			pst = con.prepareStatement( sql );
			pst.setString( 1, uname );
			rs = pst.executeQuery();

			while( rs.next() ){
				BaseTask t = mapping( rs );
				map.put( t.getTemplet().getTempletId(), t );
			}
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
			//return map; 
		}
		finally{			
			DatabaseUtil.close( rs, pst, con );
		}
		return map;
	}
	
	private BaseTask mapping( ResultSet rs ) throws SQLException {
		
		short templetId = rs.getShort( "templet_id" );
		
		BaseTask task = TaskTempletCfg.getTempletById(templetId ).createTask();
		task.setAcceptSec(rs.getInt( "accept_sec" ));
		task.setDoneSec(rs.getInt("done_sec"));
		task.setAcceptAwardSec(rs.getInt("accept_award_sec"));
		
		task.parseParamFromStr( rs.getString("parm") );
		task.setStatus( TaskStatus.fromNum( rs.getByte("status") ) );
		return task;
	}	
	
	/**
	 * 添加一个可接任务
	 * @param task		任务
	 * @param uname		玩家名<br>
	 * 
	 * status				字段缺省为可接，不用写入<br>
	 * accept_award_sec		字段缺省为0，不用写入<br>
	 * done_sec				字段缺省为0，不用写入<br>
	 * @return
	 */
	ErrorCode create( BaseTask task, String uname ) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;								  
		String sql = "insert into task_base (uname, templet_id, accept_sec, parm) "
			+ "values (?, ?, ?, ?)";
		int	i = 1;
		try {
			pst = con.prepareStatement( sql );
			pst.setString( i++, uname );
			short s = task.getTemplet().getTempletId();
			pst.setShort( i++, s );
			pst.setInt( i++, task.getAcceptSec());
			pst.setString( i++, task.getParam() == null ? "" : task.getParam().toString() );
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
	 * 修改玩家任务信息，针对某些改动比较频繁且重要的字段，可考虑专门做一个语法糖，优化的事情放到以后再说
	 * @param task		任务
	 * @param uname		玩家名
	 * @return
	 * 		DB_ERROR
	 */
	ErrorCode update( BaseTask task, String uname ) {
		
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;	
		String sql = "update task_base set " +
				"accept_sec = ?, " +
				"done_sec = ?, " +
				"accept_award_sec = ?, " +
				"parm = ?, " +
				"status = ? " +
				"where templet_id = ? and uname = ?";
		
		int	i = 1;
		try {
			pst = con.prepareStatement( sql );
			pst.setInt( i++, task.getAcceptSec() );
			pst.setInt( i++, task.getDoneSec() );
			pst.setInt( i++, task.getAcceptAwardSec() );
			pst.setString( i++, task.getParam() == null ? "" : task.getParam().toString() );
			pst.setByte( i++, task.getStatus().toNum() );
			pst.setShort( i++, task.getTemplet().getTempletId() );
			pst.setString( i++, uname );
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
