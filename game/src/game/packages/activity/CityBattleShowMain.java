package game.packages.activity;

import java.nio.ByteBuffer;

import user.UserInfo;

import game.packages.*;

@PacketDescrip(desc = "��ʾ��ս�����")
public class CityBattleShowMain extends BasePacket {

	public final short	packetNo	= 3;

	@Override
	public void run ( UserInfo user, ByteBuffer[] buffers ) {
		System.out.println( this.getClass().getName() );

	}

	@Override
	public short getPacketNo () {
		return packetNo;
	}

}