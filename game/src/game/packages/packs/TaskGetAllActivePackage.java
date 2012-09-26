/**
 * 
 */
package game.packages.packs;

import game.packages.BasePackage;
import game.packages.PackageDescrip;
import game.task.BaseTask;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

import user.UserInfo;

/**
 * 
 * @author liukun
 * 2012-9-25
 */

@PackageDescrip(desc = "获取玩家的所有活动任务")
public class TaskGetAllActivePackage  extends BasePackage {

	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		Map<Short,BaseTask> activeTasks = user.getTaskManager().getAllActive();
		ByteBuffer buffer = buildEmptyPackage( 1024 );
		buffer.put( (byte) activeTasks.size() );
		for( BaseTask t : activeTasks.values() ){
			TaskGetPackage.buildTask( t, buffer );			
		}
		
		sendPackage( user.getCon(), buffer );
	}

}
