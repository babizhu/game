/**
 * 
 */
package game.packages.packs;

import game.packages.BasePackage;
import game.packages.PackageDescrip;

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

@PackageDescrip(desc = "创建新玩家", structure = "")

public class UserCreatePackage extends BasePackage {

	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {

		
	}

	public void run( INonBlockingConnection con, ByteBuffer buf ) throws IOException {
		String name = util.BaseUtil.decodeString( buf );		//用户名
		UserInfo user = new UserInfo(con, name);

		String nickName = util.BaseUtil.decodeString( buf );	//昵称
		user.setNickName(nickName);
		byte sex = buf.get();									//性别
		user.setSex(sex);
		
		
		ErrorCode code = UserManager.getInstance().create( user );
		
		ByteBuffer buffer = buildEmptyPackage( 8 );
		buffer.putShort( (short) code.ordinal() );			//错误代码
		
		sendPackage( con, buffer );
		
	}

}
