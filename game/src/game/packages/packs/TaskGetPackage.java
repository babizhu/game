/**
 * 
 */
package game.packages.packs;

import game.packages.BasePackage;
import game.task.BaseTask;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;
import util.BaseUtil;

/**
 * 获取玩家的某个指定任务，通常应该是服务器主动发起的
 * @author liukun
 * 2012-9-25
 */
public class TaskGetPackage  extends BasePackage {

	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run( UserInfo user, ByteBuffer buf ) throws IOException {
		
		
		
		
	}
	
	public void run( UserInfo user, short templetId ) throws IOException{
		BaseTask task = user.getTaskManager().getTaskCopyByTempletId(templetId);
		if( task != null ){
		ByteBuffer buffer = buildEmptyPackage( 1024 );
			buffer.putShort( templetId );
			buffer.put( task.getStatus().toNum() );
			BaseUtil.encodeString( buffer, task.getParam().toString() );
			sendPackage( user.getCon(), buffer );
		}
	}

}
