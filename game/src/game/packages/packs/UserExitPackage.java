package game.packages.packs;

import java.nio.ByteBuffer;

import user.UserInfo;

import game.packages.*;

@PacketDescrip(desc = "玩家退出包")
public class UserExitPackage extends BasePackage {


	@Override
	public void run ( UserInfo user, ByteBuffer buf ) {
		System.out.println( this.getClass().getName() );
		ByteBuffer buff = ByteBuffer.allocate( 1024 );
		super.sendPacket( user, buff );

	}


}
