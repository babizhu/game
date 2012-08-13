package game.packages;

import java.nio.ByteBuffer;

import user.UserInfo;



public abstract class BasePackage implements IPackage {

	static ByteBuffer header = ByteBuffer.allocate( 10 );
	static ByteBuffer footer = ByteBuffer.allocate( 10 );
	
	static{
		
		header.put( (byte) 127 );
		footer.put( (byte) 127 );
	}
	//public final int ID = 0;
	
	@Override
	public abstract void run( UserInfo user, ByteBuffer buf );
	
	public void sendPacket( UserInfo user, ByteBuffer buff ){
		ByteBuffer[] b = new ByteBuffer[]{header, buff, footer};
		user.sendPacket( b );
		
	}
	
	@Override
	public abstract short getPacketNo ();
	
	
	

}
