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
	public void sendPacket( UserInfo user, ByteBuffer buffer ){
		buffer.putShort( 3, (short) (buffer.position() - 5) );//设置内容长度
		buffer.put( FOOT );
				
		buffer.flip();
		
//		ByteBuffer[] b = new ByteBuffer[]{header, buff, footer};
		user.sendPacket( buffer );
		
		System.out.println( toString(buffer) );
	}
	
	protected ByteBuffer buildEmptyPackage( int capacity ){
		ByteBuffer buff = ByteBuffer.allocate(capacity);
		buff.put( HEAD );
		buff.putShort( getPacketNo() );
		buff.putShort( (short) 0 );//长度占位符
		return buff;
	}

	public String toString( ByteBuffer buffer ) {
		System.out.println( buffer );
		buffer.flip();
		String str = "HEAD:" + buffer.get() + "\n";;
		str += "包号:" + buffer.getShort() + "\n";
		int len =  buffer.getShort();
		str += "包长:" + len + "\n";
		byte[] data = new byte[len];
		buffer.get( data );
		str += "FOOT:" + buffer.get();
		return str;
	}
	@Override
	public abstract short getPacketNo ();
	
	
	

}
