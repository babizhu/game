package game.packages;

import java.nio.ByteBuffer;

import user.UserInfo;




public interface IPackage {

	public void run( UserInfo user, ByteBuffer buf ); 
	//public int getPacketNo();

	short getPackageNo ();
}
