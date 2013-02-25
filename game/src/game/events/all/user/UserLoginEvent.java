package game.events.all.user;

import game.events.EventBase;
import game.events.EventDescrip;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.xsocket.connection.INonBlockingConnection;

import user.UserInfo;
import user.UserManager;
import util.UtilBase;
import util.ErrorCode;


@EventDescrip(desc = "玩家登陆包", structure = "short用户名长度,byte[]用户名")
public class UserLoginEvent extends EventBase {
	
	@Override
	public void run( UserInfo user, ByteBuffer buf ) throws IOException {
		
	}
	
	public void run( INonBlockingConnection con, ByteBuffer buf ) throws IOException {
		ErrorCode code = UserManager.getInstance().login( con, buf );
		
		ByteBuffer buffer = buildEmptyPackage( 1024 );
		buffer.putShort( (short) code.ordinal() );				//
		
		if( code == ErrorCode.SUCCESS ){
			String name = (String) con.getAttachment();
			UserInfo user = UserManager.getInstance().getUserByName( name );
			UtilBase.encodeString( buffer, user.getNickName() );	//昵称
			buffer.put( user.getSex() );							//性别
			buffer.put( (byte) (user.isAdult() ? 1 : 0)  );			//是否成年
			buffer.putShort( user.getStrength() );					//体力
			buffer.putInt( user.getCash()  );						//金币
			buffer.putShort( user.getLoginCount() );				//登陆次数	
			buffer.putInt( user.getCreateTime() );					//创建时间
		}
		sendPackage( con, buffer );

	}
	
}
