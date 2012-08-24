package game.packages.packs;

import game.packages.BasePackage;
import game.packages.PackageNoDefine;
import game.packages.PacketDescrip;

import java.nio.ByteBuffer;
import user.UserInfo;
import user.UserManager;
import util.BaseUtil;


@PacketDescrip(desc = "玩家登陆包", structure = "short用户名长度,byte[]用户名")

public class LoginPackage extends BasePackage {

	
	private static  final PackageNoDefine packageNo = PackageNoDefine.USER_LOGIN;
	@Override
	public void run( UserInfo user, ByteBuffer buf ) {
				
		String name = util.BaseUtil.decodeString( buf );
		user.setName( name );
		
		//TODO 进行安全监测。包括验证运营商发送过来的key值等信息
		
		UserManager.getInstance().login( user );
		ByteBuffer buffer = buildEmptyPackage( 1024 );
		BaseUtil.encodeString( buffer, user.getName() );
		buffer.put( user.getStatus().toNum() );			//玩家的状态
		

		
		sendPacket( user, buffer );

	}
	@Override
	public short getPacketNo () {
		return packageNo.toNum();
	}
}
