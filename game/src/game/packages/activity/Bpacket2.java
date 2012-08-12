package game.packages.activity;

import java.nio.ByteBuffer;

import user.UserInfo;

import game.packages.*;




@PacketDescrip(desc = "ɨ�����")

public class Bpacket2 extends BasePacket {

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
