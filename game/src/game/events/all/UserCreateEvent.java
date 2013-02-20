/**
 * 
 */
package game.events.all;

import game.events.EventBase;
import game.events.EventDescrip;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.xsocket.connection.INonBlockingConnection;

import user.UserInfo;
import user.UserManager;
import util.ErrorCode;

/**
 * @author liukun
 * 2012-8-26
 */

@EventDescrip(desc = "创建新玩家", structure = "")

public class UserCreateEvent extends EventBase {

	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {

		
	}

	public void run( INonBlockingConnection con, ByteBuffer buf ) throws IOException {
		String name = util.UtilBase.decodeString( buf );		//用户名
		String nickName = util.UtilBase.decodeString( buf );	//昵称
		byte sex = buf.get();									//性别

		UserInfo user = new UserInfo(con, name, nickName, sex );
				
		ErrorCode code = UserManager.getInstance().create( user );
		
		ByteBuffer buffer = buildEmptyPackage( 8 );
		buffer.putShort( (short) code.ordinal() );			//错误代码
		
		sendPackage( con, buffer );
		
	}

}
