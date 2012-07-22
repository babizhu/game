package game.packages;

import java.nio.ByteBuffer;

import user.UserInfo;




public interface IPacket {

	public void run( UserInfo user, ByteBuffer[] buffers ); 
	//public int getPacketNo();

	short getPacketNo ();
}
