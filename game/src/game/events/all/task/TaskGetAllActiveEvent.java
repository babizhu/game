/**
 * 
 */
package game.events.all.task;

import game.events.EventBase;
import game.events.EventDescrip;
import game.task.TaskBase;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

import user.UserInfo;

/**
 * 
 * @author liukun
 * 2012-9-25
 */

@EventDescrip(desc = "获取玩家的所有活动任务")
public class TaskGetAllActiveEvent  extends EventBase {

	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		Map<Short,TaskBase> activeTasks = user.getTaskManager().getAllActiveTasksCopy();
		ByteBuffer buffer = buildEmptyPackage( 1024 );
		buffer.put( (byte) activeTasks.size() );
		for( TaskBase t : activeTasks.values() ){
			TaskGetEvent.buildTaskBytes( t, buffer );	
		}
		
		sendPackage( user.getCon(), buffer );

	}

}
