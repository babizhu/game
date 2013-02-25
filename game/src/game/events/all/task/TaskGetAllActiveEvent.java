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

	private static final int PACK_LEN = 1024;
	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		synchronized (user) {
			
			Map<Short,TaskBase> activeTasks = user.getTaskManager().getAllActiveTasks();
			ByteBuffer response = buildEmptyPackage( PACK_LEN );
			response.put( (byte) activeTasks.size() );
			for( TaskBase t : activeTasks.values() ){
				t.buildTransformStream(response);
			}
			
			sendPackage( user.getCon(), response );
		}

	}

}
