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
 * @author liukun
 * 2012-9-25
 */

@EventDescrip(desc = "玩家完成任务后发送的领奖包")
public class TaskAcceptAwardEvent  extends EventBase {

	private static final int PACK_LEN = 16;
	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run( UserInfo user, ByteBuffer buf ) throws IOException {
		short templetId = buf.getShort();
		ByteBuffer response = buildEmptyPackage( PACK_LEN );
		synchronized (user) {
			
			ErrorCode code = user.getTaskManager().acceptAward( templetId );
			
			
			response.putShort( (short) code.ordinal() );
			response.putShort( templetId );
			sendPackage( user.getCon(), response );
		}
		
	}

}
