/**
 * 
 */
package game.events.all.message;

import game.events.EventBase;
import game.events.EventDescrip;

import java.io.IOException;
import java.nio.ByteBuffer;
import user.UserInfo;

/**
 * @author liukun
 * 2012-9-25
 */

@EventDescrip(desc = "服务器主动发送信息给客户端")
public class SendMsgEvent  extends EventBase {

	private static final int PACK_LEN = 1024;
	/* 
	 * 这里有很大的优化余地，似乎应该用asReadOnlyBuffer处理？
	 * 由此可知聊天确实是一个比较特殊的包
	 */
	@Override
	public void run( UserInfo user, ByteBuffer buf ){
				
		ByteBuffer response = buildEmptyPackage( PACK_LEN );		
		response.put( buf );
		//UtilBase.encodeString( response, msg );
		try{
			sendPackage( user.getCon(), response );
		}
		catch( IOException e){
			System.out.println( e );
		}
	}
}
