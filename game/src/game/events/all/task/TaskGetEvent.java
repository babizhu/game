/**
 * 
 */
package game.events.all.task;

import game.events.EventBase;
import game.events.EventDescrip;
import game.task.TaskBase;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;
import util.UtilBase;

/**
 * 
 * @author liukun
 * 2012-9-25
 */

@EventDescrip(desc = "告知玩家的某个指定任务的详细信息，通常应该是服务器主动发起的")
public class TaskGetEvent extends EventBase {

	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run( UserInfo user, ByteBuffer buf ) throws IOException {	
		short templetId = buf.getShort();
		TaskBase task = user.getTaskManager().getTaskCopyByTempletId(templetId);
		if( task != null ){
			ByteBuffer buffer = buildEmptyPackage( 1024 );
			buildTaskBytes( task, buffer);
			sendPackage( user.getCon(), buffer );
		}
		
		
	}	
	public void run( UserInfo user, short templetId ) throws IOException{
		TaskBase task = user.getTaskManager().getTaskCopyByTempletId(templetId);
		if( task != null ){
			ByteBuffer buffer = buildEmptyPackage( 1024 );
			buildTaskBytes( task, buffer);
			sendPackage( user.getCon(), buffer );
		}
	}
	
	/**
	 * 组成一个任务的ByteBuffer内容，方便其他函数调用
	 * @param task
	 * @param buffer
	 */
	static void buildTaskBytes( TaskBase task, ByteBuffer buffer ){
		buffer.putShort( task.getTemplet().getTempletId() );
		buffer.put( task.getStatus().toNum() );
		String param = task.getParam() == null ? "" : task.getParam().toString();
		UtilBase.encodeString( buffer, param );

	}

}
