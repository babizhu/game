package game.packages.activity;

import java.nio.ByteBuffer;

import user.UserInfo;

import game.packages.*;


@PacketDescrip(desc = "ÏÔÊ¾¾«Ó¢Ãæ°å")
public class CopyOfPacket2 extends AbstractPacket {

	
	public short packetNo = 1;
	@Override
	public void run ( UserInfo user, ByteBuffer[] buffers ) {
		System.out.println( this.getClass().getName() );

	}
	@Override
	public short getPacketNo () {
		return packetNo;
	}

}
