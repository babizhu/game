package game.packages.packs;

import game.packages.BasePackage;
import game.packages.PacketDescrip;

import java.nio.ByteBuffer;

import user.UserInfo;
import user.UserManager;
import util.ErrorCode;


@PacketDescrip(desc = "玩家登陆包", structure = "short用户名长度,byte用户名")

public class LoginPackage extends BasePackage {

	
	private static short packetNo = 1;
	@Override
	public void run ( UserInfo user, ByteBuffer buf ) {
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
		//运行游戏相应逻辑，产生需要传给客户端的ByteBuffer buf
		//String name = decod
		//user.setName( name );
		ErrorCode eCode = UserManager.getInstance().login(user);
		if( eCode != ErrorCode.SUCCESS ){
			//TODO 发送错误消息
		}
		sendPacket( user, buf );
	}
	@Override
	public short getPacketNo () {
		return packetNo;
	}
}
