package game.packages.packs;

import game.packages.BasePackage;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.xsocket.connection.IBlockingConnection;

public abstract class BasePackageTest {

	/**
	 * 发送包的次数
	 */
	public final int			count;
	
	public BasePackageTest( int count ) {
		this.count = count;
	}
	/**
	 * 创建一个发送到服务器的空包
	 * @param capacity
	 * @return
	 */
	ByteBuffer createEmptyPackage( int capacity ){
		
		ByteBuffer buff = ByteBuffer.allocate(capacity);
		buff.put( BasePackage.HEAD );
		buff.putShort( getPacketNo() );
		buff.putShort( (short) 0 );//长度占位符
		return buff;
	}
	/**
	 * 向服务器端发送包
	 * @param user
	 * @param buff
	 * 		包括包号(short)，包长(short)，包内容(byte[])
	 */
	public void sendPacket( IBlockingConnection conn, ByteBuffer buffer ){
		buffer.putShort( 3, (short) (buffer.position() - 5) );//设置内容长度
		buffer.put( BasePackage.FOOT );				
		buffer.flip();
		
//		IBlockingConnection conn = user.getConn();
		try {
			conn.write( buffer );
		} catch (IOException e) {
			//logger.debug( user + " " + e.toString() );
		}
	}
	/**
	 * 确保此包是完整的
	 * 去除包头，长度等信息，返回包内容
	 * 注意	：此数据包括1 byte 长度的包尾
	 * @return
	 * @throws IOException 
	 */
	public ByteBuffer getData( IBlockingConnection nbc  ) throws IOException{

		byte head = nbc.readByte();
		short packageNo = nbc.readShort();
		short len = nbc.readShort();
		byte[] data = nbc.readBytesByLength( len );
		byte foot = nbc.readByte();
		if( head != BasePackage.HEAD && foot != BasePackage.FOOT && packageNo != getPacketNo() ){
			System.out.println( "error : head=" + head + ",packageNo=" + packageNo + ",foot=" + foot );
			return null;
		}
		return ByteBuffer.wrap( data );
	}
	
	/**
	 * 检测服务器端所发送的首尾标识位是否正确
	 * @param head
	 * @param foot
	 * @return
	 * 		true		首尾包号正确
	 * 		false		错误
	 */
	boolean checkInputData( byte head, byte foot ){
		if( head != BasePackage.HEAD || foot != BasePackage.FOOT ){
			return false;
		}
		return true;
	}
	
	public abstract short getPacketNo ();
	
	/**
	 * 重载以实现不同包发送不同数据
	 * @param name
	 * @return
	 */
	public abstract ByteBuffer createContent( String name );
	
	/**
	 * 解析服务器端的返回包
	 * @param buf
	 */
	public abstract void parse( ByteBuffer buf );
	
	public abstract void run() throws IOException, InterruptedException;
}
