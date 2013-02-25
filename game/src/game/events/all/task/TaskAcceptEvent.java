/**
 * 
 */
package game.events.all.task;

import game.events.EventBase;
import game.events.EventDescrip;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;
import util.ErrorCode;

/**
 * 
 * @author liukun
 * 2012-9-25
 */

@EventDescrip(desc = "玩家接任务")
public class TaskAcceptEvent  extends EventBase {

	private static final int PACK_LEN = 16;
	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		short templetId = buf.getShort();
		
		synchronized (user) {
			
			ErrorCode code = user.getTaskManager().acceptTask( templetId );
			
			ByteBuffer buffer = buildEmptyPackage( PACK_LEN );			
			buffer.putShort( (short) code.ordinal() );
			buffer.putShort( templetId );
			sendPackage( user.getCon(), buffer );
		}
	}

}
