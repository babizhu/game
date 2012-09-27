package game.packages.packs.task;


import static org.junit.Assert.assertEquals;
import game.db.DatabaseUtil;
import game.packages.Packages;
import game.packages.packs.BasePackageTest;
import game.packages.packs.UserLoginPackageTest;
import game.task.enums.TaskStatus;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import util.ErrorCode;
import define.SystemCfg;

public class TaskAcceptAwardPackageTest  extends BasePackageTest{
	
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
	
		ByteBuffer buf = new UserLoginPackageTest().sendPackage( nbc, name );
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );

		short templetId = 10001;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
		/************************可领奖正常任务**************************/
		
		
		templetId = 10001;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.TASK_NOT_FOUND, code );
		/************************已领奖（彻底完成）任务**************************/
		
		templetId = -1;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.TASK_NOT_FOUND, code );
		/************************不存在的任务**************************/
		
		templetId = 10002;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.UNKNOW_ERROR, code );
		/************************未接任务**************************/
		
		templetId = 10004;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.UNKNOW_ERROR, code );
		/************************已接任务**************************/
		
		templetId = 10000;
		buf = sendPackage( nbc,templetId );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.TASK_NOT_FOUND, code );
		/************************已完成任务**************************/
		
		templetId = 10001;
		restoreTask( templetId );
		//Thread.sleep( 2000 );
	}
	/**
	 * 还原修改的任务状态方便下一次测试
	 * 
	 *  1、关闭服务器
	 *  2、把玩家bbz的10001号任务的状态设置为3
	 * @param templetId
	 */
	private void restoreTask( short templetId ){
		 
		String name = "bbz";
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;	
		String sql = "update task_base set status = ? " +
				"where templet_id = ? and name = ?";
		
		int	i = 1;
		try {
			pst = con.prepareStatement( sql );
			pst.setByte( i++, TaskStatus.NO_REWARD.toNum() );
			pst.setShort( i++, templetId );
			pst.setString( i++, name );
			pst.executeUpdate();
		} catch (SQLException e) {
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		
	}
	@Override
	public short getPacketNo() {
		// TODO Auto-generated method stub
		return Packages.TASK_ACCEPT_AWARD.toNum();
	}
}
