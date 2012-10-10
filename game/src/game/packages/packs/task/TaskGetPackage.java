/**
 * 
 */
package game.packages.packs.task;

import game.packages.BasePackage;
import game.packages.PackageDescrip;
import game.task.BaseTask;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;
import util.BaseUtil;

/**
 * 
 * @author liukun
 * 2012-9-25
 */

@PackageDescrip(desc = "告知玩家的某个指定任务的详细信息，通常应该是服务器主动发起的")
public class TaskGetPackage  extends BasePackage {

	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run( UserInfo user, ByteBuffer buf ) throws IOException {	
		short templetId = buf.getShort();
		BaseTask task = user.getTaskManager().getTaskCopyByTempletId(templetId);
		if( task != null ){
			ByteBuffer buffer = buildEmptyPackage( 1024 );
			buildTaskBytes( task, buffer);
			sendPackage( user.getCon(), buffer );
		}
		
		
	}	
	public void run( UserInfo user, short templetId ) throws IOException{
		BaseTask task = user.getTaskManager().getTaskCopyByTempletId(templetId);
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
	static void buildTaskBytes( BaseTask task, ByteBuffer buffer ){
		buffer.putShort( task.getTemplet().getTempletId() );
		buffer.put( task.getStatus().toNum() );
		String param = task.getParam() == null ? "" : task.getParam().toString();
		BaseUtil.encodeString( buffer, param );

	}

}
