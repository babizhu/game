package test;

import game.packages.BasePackage;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import util.BaseUtil;

/**
 * 
 * @author admin
 * 2012-8-21 上午09:06:13
 */
public class SendPackage {
	
	ByteBuffer login(){
		String name = "bbzs";
		ByteBuffer data = ByteBuffer.allocate( 10 );
		BaseUtil.encodeString(data, name );
		data.putInt(2);
		data.flip();
		return data;
	}
	
	ByteBuffer createContent( short packageNo ){
		ByteBuffer pack = null;
		ByteBuffer data = null;
		switch( packageNo ){
		case 1:
			data = login();
			break;
		default:
			break;
		}
		pack = ByteBuffer.allocate( 6 + data.limit() );
		
		pack.put( BasePackage.HEAD );
		pack.putShort( packageNo );
		pack.putShort( (short) data.limit() );
		pack.put( data );
		pack.put( BasePackage.FOOT );
		pack.flip();
		
		return pack;
	}
	void sendPackage( short packageNo ){
		try {
			
			Socket socket = new Socket();
			InetSocketAddress addr = new InetSocketAddress( "127.0.0.1", 8000 );
			socket.connect( addr );
				
			ByteBuffer buf = createContent(packageNo);
//			
			socket.getOutputStream().write( buf.array() );

//			socket.getOutputStream().flush();
			Thread.sleep( 100 );
			System.out.println( buf.limit() );
//			socket.getOutputStream().write( buf.array() );
//			byte[] b = new byte[100];
//			System.out.println( socket.getInputStream().read(b) + "字节" );
//			System.out.println( (char)b[7] +"" + (char)b[7]+"" + (char)b[7]);
//			
			socket.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
}

	public static void main ( String[] args ) {
		for( int i = 0; i < 10000; i++ ){
			new SendPackage().sendPackage( (short) 1 );
		}
	}
}
