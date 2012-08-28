/**
 * 
 */
package game.packages.packs;

import game.packages.BasePackage;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;
import user.UserManager;
import util.ErrorCode;

/**
 * @author liukun
 * 2012-8-26
 */
public class UserCreatePackage extends BasePackage {

	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {

		String name = util.BaseUtil.decodeString( buf );		//用户名
		user.setName( name );
		String nickName = util.BaseUtil.decodeString( buf );	//昵称
		user.setNickName(nickName);
		byte sex = buf.get();									//性别
		user.setSex(sex);
		
		
		ErrorCode eCode =UserManager.getInstance().create(user);
		
		ByteBuffer buffer = buildEmptyPackage( 1024 );
		buffer.putShort( (short) eCode.ordinal() );			//错误代码
		
		sendPackage( user, buffer );
	}

}
