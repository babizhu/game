package game.events.all.task;


import static org.junit.Assert.assertEquals;
import game.events.EventManager;
import game.events.all.BaseEventTest;
import game.events.all.UserLoginEventTest;
import game.task.cfg.TaskTempletCfg;
import game.task.enums.TaskStatus;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import util.ErrorCode;
import util.db.DatabaseUtil;
import define.SystemCfg;

public class TaskAcceptEventTest extends BaseEventTest {

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		TaskTempletCfg.init();
	}
	

	private ByteBuffer sendPackage( IBlockingConnection nbc, short templetId ) throws IOException{
		
		ByteBuffer buf = createContent( templetId );
		sendPacket( nbc, buf );
		return getData( nbc );
	}
	
	private ByteBuffer createContent( short templetId ) {
		ByteBuffer buf = createEmptyPackage(1024);
		buf.putShort(templetId);
		return buf;
	}

	@Test
	public void test() throws IOException, InterruptedException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		String name = "bbz";
	
		ByteBuffer buf = new UserLoginEventTest().sendPackage( nbc, name );
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );

		short templetId = 10002;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
		/************************可接任务**************************/
		
		
		templetId = 10002;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.TASK_HAS_ACCEPTED, code );
		/************************已接任务**************************/
		
		templetId = -1;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.TASK_NOT_FOUND, code );
		/************************不存在的任务**************************/
		
		templetId = 10003;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.TASK_HAS_ACCEPTED, code );
		/************************已经完成尚未领过奖的任务**************************/
		
		templetId = 10000;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.TASK_NOT_FOUND, code );
		/************************领过奖的任务**************************/
		
		templetId = 10004;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.USER_LEVEL_NOT_ENOUGH, code );
		/************************接一个玩家等级不够的任务**************************/
		
		templetId = 10002;
		restoreTask( templetId );
		//Thread.sleep( 2000 );
	}

	/**
	 * 还原修改的任务状态方便下一次测试
	 * 
	 *  1、关闭服务器
	 *  2、把玩家bbz的10002号任务的状态设置为1
	 * @param templetId
	 */
	private void restoreTask( short templetId ){
		 
		String name = "bbz";
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;	
		String sql = "update task_base set status = ? " +
				"where templet_id = ? and uname = ?";
		
		int	i = 1;
		try {
			pst = con.prepareStatement( sql );
			pst.setByte( i++, TaskStatus.CAN_ACCEPT.toNum() );
			pst.setShort( i++, templetId );
			pst.setString( i++, name );
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		
	}
	
	@Override
	public short getEventId() {
		// TODO Auto-generated method stub
		return EventManager.TASK_ACCEPT.toNum();
	}
}
