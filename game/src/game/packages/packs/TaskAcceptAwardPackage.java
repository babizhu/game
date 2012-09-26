/**
 * 
 */
package game.packages.packs;

import game.packages.BasePackage;
import game.packages.PackageDescrip;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;
import util.ErrorCode;

/**
 * @author liukun
 * 2012-9-25
 */

@PackageDescrip(desc = "玩家完成任务后发送的领奖包")
public class TaskAcceptAwardPackage  extends BasePackage {

	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		short templetId = buf.getShort();
		ErrorCode code = user.getTaskManager().acceptAward( templetId );
		
		ByteBuffer buffer = buildEmptyPackage( 16 );

		buffer.putShort( (short) code.ordinal() );
		buffer.putShort( templetId );
		sendPackage( user.getCon(), buffer );
		
	}

}
