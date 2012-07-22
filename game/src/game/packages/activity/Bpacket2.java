package game.packages.activity;

import java.nio.ByteBuffer;

import user.UserInfo;

import game.packages.*;




@PacketDescrip(desc = "É¨µ´Ãæ°å")

public class Bpacket2 extends AbstractPacket {

	public final short packetNo = 2;
	@Override
	public void run ( UserInfo user, ByteBuffer[] buffers ) {
		System.out.println( this.getClass().getName() );

	}
	@Override
	public short getPacketNo () {
		return packetNo;
	}
}
