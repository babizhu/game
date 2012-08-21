package test;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SendPackage {
	static void sendPackage(){
		try {
			Socket socket = new Socket();
			InetSocketAddress addr = new InetSocketAddress( "127.0.0.1", 8000 );
			socket.connect( addr );
			ByteBuffer buf = ByteBuffer.allocate( 6+8 );
			buf.put( (byte) 127 );
			short packageNo = 20000;
			ByteBuffer data = ByteBuffer.allocate( 8 );
			data.putInt(1);
			data.putInt(2);
			data.flip();
			
			
			buf.putShort( packageNo );
			buf.putShort( (short) data.limit() );
			buf.put( data );
			buf.put( (byte) 126 );
			buf.flip();
//			buf.get( dst )
			
			
//			By
//			
			socket.getOutputStream().write( buf.array() );
//			socket.getOutputStream().flush();
			Thread.sleep( 10000 );
			socket.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
}

public static void main ( String[] args ) {
	sendPackage();
}
}
