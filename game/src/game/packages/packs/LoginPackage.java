package game.packages.packs;

import game.packages.BasePackage;
import game.packages.PacketDescrip;

import java.nio.ByteBuffer;

import user.UserInfo;
import user.UserManager;
import util.BaseUtil;
import util.ErrorCode;


@PacketDescrip(desc = "玩家登陆包", structure = "short用户名长度,byte用户名")

public class LoginPackage extends BasePackage {

	
	private static short packetNo = 1;
	@Override
	public void run ( UserInfo user, ByteBuffer buf ) {
		/**
		int i = buf.getInt();
		switch( i ){
		case 1:
			System.out.println( "login" );
			break;
		case 2:
			System.out.println( "exit" );
			break;
		default:
			System.out.println( "another reson" );
		}
		*/
		
		String name = util.BaseUtil.decodeString( buf );
		user.setName( name );
		
		//TODO 进行安全监测。包括验证运营商发送过来的key值等信息
		
		ErrorCode eCode = UserManager.getInstance().login(user);
		if( eCode != ErrorCode.SUCCESS ){
			//TODO 发送错误消息
		}
//		ByteBuffer bf = ByteBuffer.allocate( 1024 );
		//bf.putShort( packetNo );
		ByteBuffer data = buildEmptyPackage( 1024 );
		BaseUtil.encodeString( data, user.getName() );
		sendPacket( user, data );
	}
	@Override
	public short getPacketNo () {
		return packetNo;
	}
}
