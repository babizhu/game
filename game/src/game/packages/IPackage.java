package game.packages;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;




public interface IPackage {

	public void run( UserInfo user, ByteBuffer buf ) throws IOException; 
	//public int getPacketNo();

	short getPackageNo ();
}
