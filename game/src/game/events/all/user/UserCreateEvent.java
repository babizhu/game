/**
 * 
 */
package game.events.all.user;

import game.events.EventBase;
import game.events.EventDescrip;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.xsocket.connection.INonBlockingConnection;

import user.UserInfo;
import user.UserManager;
import util.ErrorCode;
import util.UtilBase;

/**
 * @author liukun
 * 2012-8-26
 */

@EventDescrip(desc = "创建新玩家", structure = "")

public class UserCreateEvent extends EventBase {

	private static final int PACK_LEN = 8;
	
	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {

		
	}

	public void run( INonBlockingConnection con, ByteBuffer buf ) throws IOException {
		String name = UtilBase.decodeString( buf );		//用户名
		String nickName = UtilBase.decodeString( buf );	//昵称
		byte sex = buf.get();							//性别

		UserInfo user = new UserInfo(con, name, nickName, sex );
				
		ErrorCode code = UserManager.getInstance().create( user );
		
		ByteBuffer buffer = buildEmptyPackage( PACK_LEN );
		buffer.putShort( (short) code.ordinal() );			//错误代码
		
		/**
		 * 这里存在一定的隐患，如果另外的线程把con关闭了，会怎么样？
		 */
		sendPackage( con, buffer );
		
	}

}
