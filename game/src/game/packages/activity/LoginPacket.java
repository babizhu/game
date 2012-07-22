package game.packages.activity;

import java.nio.ByteBuffer;

import user.UserInfo;

import game.packages.*;

@PacketDescrip(desc = "Íæ¼ÒµÇÂ½°ü")
public class LoginPacket extends AbstractPacket {

	public final short	packetNo	= 6;

	@Override
	public void run ( UserInfo user, ByteBuffer[] buffers ) {
		System.out.println( this.getClass().getName() );
		ByteBuffer buff = ByteBuffer.allocate( 1024 );
		super.sendPacket( user, buff );

	}

	@Override
	public short getPacketNo () {
		return packetNo;
	}

}
