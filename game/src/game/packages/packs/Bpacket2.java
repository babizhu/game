package game.packages.packs;

import java.nio.ByteBuffer;

import user.UserInfo;

import game.packages.*;


@PacketDescrip(desc = "测试1")


public class Bpacket2 extends BasePackage {

	private static final short packetNo = 2;
	@Override
	public void run ( UserInfo user, ByteBuffer buf ) {
		System.out.println( this.getClass().getName() );

	}
	@Override
	public short getPacketNo () {
		return packetNo;
	}

	public static void main ( String[] args ) {
		//Bpacket2 d = new Bpacket2();
	}
}
