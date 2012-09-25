/**
 * 
 */
package game.packages.packs;

import game.packages.BasePackage;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;
import util.ErrorCode;

/**
 * 玩家发送的领奖包
 * @author liukun
 * 2012-9-25
 */
public class TaskAcceptAwardPackage  extends BasePackage {

	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		short templetId = buf.getShort();
		ErrorCode code = user.getTaskManager().acceptAward( templetId );
		
		ByteBuffer buffer = buildEmptyPackage( 4 );
		buffer.putShort( (short) code.ordinal() );
		buffer.putShort( templetId );
		sendPackage( user.getCon(), buffer );
		
	}

}
