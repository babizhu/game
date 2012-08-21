package game.packages;

import java.nio.ByteBuffer;

import user.UserInfo;



public abstract class BasePackage implements IPackage {
	
	public static final byte HEAD		= 127;
	public static final byte FOOT		= 126;


	public BasePackage() {
//		header.put( HEAD );//包头
//		header.putShort( getPacketNo() );//包号
		//包长为一个short，具体值由相应函数写入
	}
	
	/**
	 * 从客户端收取包并进行逻辑处理
	 */
	@Override
	public abstract void run( UserInfo user, ByteBuffer buf );
	
	/**
	 * 向客户端发送包
	 * @param user
	 * @param buff
	 * 		包括包号(short)，包长(short)，包内容(byte[])
	 */
	public void sendPacket( UserInfo user, ByteBuffer buff ){
		buff.flip();
		ByteBuffer data = ByteBuffer.allocate( buff.limit() + 2 );
		data.put(HEAD);
		data.put( buff );
		data.put( FOOT );
		
		data.flip();
		
//		ByteBuffer[] b = new ByteBuffer[]{header, buff, footer};
		user.sendPacket( data );
	}
	
	@Override
	public abstract short getPacketNo ();
	
	
	

}
