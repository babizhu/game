package game.packages.activity;

import java.nio.ByteBuffer;

import user.UserInfo;

import game.packages.*;


@PacketDescrip(desc = "玩家登陆包")
public class LoginPackage extends BasePackage {

	
	private short packetNo = 1;
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

	}
	@Override
	public short getPacketNo () {
		return packetNo;
	}

}
