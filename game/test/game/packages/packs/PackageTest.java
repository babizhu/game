package game.packages.packs;

import game.packages.BasePackage;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.xsocket.connection.IBlockingConnection;


public abstract class PackageTest {
	
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
	 * 发送包到服务器
	 * @param conn
	 * @param buffer
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
	
	public abstract short getPacketNo ();

}
