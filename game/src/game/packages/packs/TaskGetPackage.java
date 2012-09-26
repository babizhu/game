/**
 * 
 */
package game.packages.packs;

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

@PackageDescrip(desc = "获取玩家的某个指定任务，通常应该是服务器主动发起的")
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
			buildTask( task, buffer);
			sendPackage( user.getCon(), buffer );
		}
		
		
	}	
	public void run( UserInfo user, short templetId ) throws IOException{
		BaseTask task = user.getTaskManager().getTaskCopyByTempletId(templetId);
		if( task != null ){
			ByteBuffer buffer = buildEmptyPackage( 1024 );
			buildTask( task, buffer);
			sendPackage( user.getCon(), buffer );
		}
	}
	
	public static void buildTask( BaseTask task, ByteBuffer buffer ){
		buffer.putShort( task.getTemplet().getTempletId() );
		buffer.put( task.getStatus().toNum() );
		String param = task.getParam() == null ? "" : task.getParam().toString();
		BaseUtil.encodeString( buffer, param );	

	}

}
