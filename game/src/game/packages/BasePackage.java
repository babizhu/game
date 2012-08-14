package game.packages;

import java.nio.ByteBuffer;

import user.UserInfo;



public abstract class BasePackage implements IPackage {

	private ByteBuffer header = ByteBuffer.allocate( 5 );
	
	/**
	 * 所有包的包尾都是一个固定值，因此用一个static处理即可
	 */
	private static final ByteBuffer footer = ByteBuffer.allocate( 1 );

	private static final int	HEADER_LIMIT	= 5;
	
	static{
		footer.put( (byte) 126 );
	}
	
	public BasePackage() {
		header.put( (byte) 127 );//包头
		header.putShort( getPacketNo() );//包号
		//包长为一个short，具体值由相应函数写入
	}
	static{
		
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
	 */
	public void sendPacket( UserInfo user, ByteBuffer buff ){
		
		header.putShort( 3, (short) buff.limit() );
		header.position( HEADER_LIMIT );
		header.flip();
		footer.flip();
		
		ByteBuffer[] b = new ByteBuffer[]{header, buff, footer};
		user.sendPacket( b );
	}
	
	@Override
	public abstract short getPacketNo ();
	
	
	

}
