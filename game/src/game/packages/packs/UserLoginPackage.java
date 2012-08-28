package game.packages.packs;

import game.packages.BasePackage;
import game.packages.PacketDescrip;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;
import user.UserManager;
import user.UserStatus;
import util.BaseUtil;


@PacketDescrip(desc = "玩家登陆包", structure = "short用户名长度,byte[]用户名")

public class UserLoginPackage extends BasePackage {
	
	@Override
	public void run( UserInfo user, ByteBuffer buf ) throws IOException {
				
		String name = util.BaseUtil.decodeString( buf );
		user.setName( name );
		
		//TODO 进行安全监测。包括验证运营商发送过来的key值等信息
		
		UserManager.getInstance().login( user );
		
		ByteBuffer buffer = buildEmptyPackage( 1024 );
		buffer.put( user.getStatus().toNum() );				//状态
		BaseUtil.encodeString( buffer, user.getName() );	//用户名
		
		if( user.getStatus() == UserStatus.LOGIN ){
			BaseUtil.encodeString( buffer, user.getNickName() );	//昵称
			buffer.put( user.getSex() );							//性别
			buffer.put( user.getLevel() );							//级别
			buffer.put( (byte) (user.isAdult()? 1 : 0)  );			//是否成年			
		}
		sendPackage( user, buffer );

	}
}
