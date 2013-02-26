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

/**
 * 
 * @author liukun
 * 2012-9-25
 */

@EventDescrip(desc = "告知玩家的某个指定任务的详细信息，通常应该是服务器主动发起的")
public class TaskGetEvent extends EventBase {

	private static final int PACK_LEN = 1024;
	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run( UserInfo user, ByteBuffer buf ) throws IOException {	
		short templetId = buf.getShort();
		
		synchronized ( user ) {
			
			TaskBase task = user.getTaskManager().getTaskByTempletId(templetId);
			if( task != null ){
				ByteBuffer response = buildEmptyPackage( PACK_LEN );
				task.buildTransformStream( response );
				sendPackage( user.getCon(), response );
			}
		}
		
		
	}	
	public void run( UserInfo user, short templetId ) throws IOException{
		
		synchronized (user) {
			
			TaskBase task = user.getTaskManager().getTaskByTempletId( templetId );
			if( task != null ){
				ByteBuffer response = buildEmptyPackage( PACK_LEN );
				task.buildTransformStream( response );
				sendPackage( user.getCon(), response );
			}
		}
	}
}
