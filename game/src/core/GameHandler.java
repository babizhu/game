/**
 * 
 */
package core;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.IIdleTimeoutHandler;
import org.xsocket.connection.INonBlockingConnection;

/**
 * @author liukun
 * 2012-8-16
 */
public class GameHandler  implements IDataHandler ,IConnectHandler ,IIdleTimeoutHandler, IDisconnectHandler{

	/* (non-Javadoc)
	 * @see org.xsocket.connection.IIdleTimeoutHandler#onIdleTimeout(org.xsocket.connection.INonBlockingConnection)
	 */
	@Override
	public boolean onIdleTimeout( INonBlockingConnection con ) throws IOException {
		System.out.println( con.getRemoteAddress() + " " + con.getRemotePort() + " onIdleTimeout" );
		return false;
	}

	/* (non-Javadoc)
	 * @see org.xsocket.connection.IConnectHandler#onConnect(org.xsocket.connection.INonBlockingConnection)
	 */
	@Override
	public boolean onConnect( INonBlockingConnection con ) throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
		System.out.println( con.getRemoteAddress() + " " + con.getRemotePort() );
		con.write("hello");
		return false;
	}

	/* (non-Javadoc)
	 * @see org.xsocket.connection.IDataHandler#onData(org.xsocket.connection.INonBlockingConnection)
	 */
	@Override
	public boolean onData( INonBlockingConnection con ) throws IOException,	BufferUnderflowException, ClosedChannelException, MaxReadSizeExceededException {
		System.out.println( con.getRemoteAddress() + " " + con.getRemotePort() + " onData" );
		return false;
	}

	/* (non-Javadoc)
	 * @see org.xsocket.connection.IDisconnectHandler#onDisconnect(org.xsocket.connection.INonBlockingConnection)
	 */
	@Override
	public boolean onDisconnect( INonBlockingConnection con ) throws IOException {
		System.out.println( con.getRemoteAddress() + " " + con.getRemotePort() + " onDisconnect" );
		return false;
	}

}
