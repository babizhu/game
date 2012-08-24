package game.packages.packs;

import java.nio.ByteBuffer;

import user.UserInfo;

import game.packages.*;

@PacketDescrip(desc = "玩家退出包")
public class ExitPackage extends BasePackage {

	private static  final PackageNoDefine packageNo = PackageNoDefine.USER_EXIT;

	@Override
	public void run ( UserInfo user, ByteBuffer buf ) {
		System.out.println( this.getClass().getName() );
		ByteBuffer buff = ByteBuffer.allocate( 1024 );
		super.sendPacket( user, buff );

	}

	@Override
	public short getPacketNo () {
		return packageNo.toNum();
	}

}
