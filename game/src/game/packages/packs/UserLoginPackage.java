package game.packages.packs;

import game.packages.BasePackage;
import game.packages.PacketDescrip;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;
import user.UserManager;
import util.BaseUtil;
import util.ErrorCode;


@PacketDescrip(desc = "玩家登陆包", structure = "short用户名长度,byte[]用户名")

public class UserLoginPackage extends BasePackage {
	
	@Override
	public void run( UserInfo user, ByteBuffer buf ) throws IOException {
				
		String name = util.BaseUtil.decodeString( buf );
		user.setName( name );
		
		//TODO 进行安全监测。包括验证运营商发送过来的key值等信息
		
		ErrorCode code = UserManager.getInstance().login( user );
		
		ByteBuffer buffer = buildEmptyPackage( 1024 );
		buffer.putShort( (short) code.ordinal() );				//
		//BaseUtil.encodeString( buffer, user.getName() );		//用户名，似乎没必要发送
		
		if( code == ErrorCode.SUCCESS ){
			
			BaseUtil.encodeString( buffer, user.getNickName() );	//昵称
			buffer.put( user.getSex() );							//性别
			buffer.put( (byte) (user.isAdult()? 1 : 0)  );			//是否成年
			buffer.putShort( user.getStrength() );					//体力
			buffer.putInt( user.getMoney()  );						//金币
			buffer.putShort( user.getLoginCount() );				//登陆次数	
			buffer.putInt( user.getCreateTime() );					//创建时间
//			
		}
		sendPackage( user, buffer );

	}
	
}
