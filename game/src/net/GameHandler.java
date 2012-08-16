
package net;

import game.packages.BasePackage;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.ConnectionUtils;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.IIdleTimeoutHandler;
import org.xsocket.connection.INonBlockingConnection;

import core.GameMainLogic;

import user.UserInfo;

/**
 * @author liukun
 * 2012-8-16
 */
public class GameHandler  implements IDataHandler ,IConnectHandler ,IIdleTimeoutHandler, IDisconnectHandler{

	private GameMainLogic gameLogic = GameMainLogic.getInstance();
	/* (non-Javadoc)
	 * @see org.xsocket.connection.IIdleTimeoutHandler#onIdleTimeout(org.xsocket.connection.INonBlockingConnection)
	 */
	@Override
	public boolean onIdleTimeout( INonBlockingConnection con ) throws IOException {
		con = ConnectionUtils.synchronizedConnection( con );
		System.out.println( con.getRemoteAddress() + " " + con.getRemotePort() + " onIdleTimeout" );
		return false;
		//return true;//不切断连接
	}

	/* (non-Javadoc)
	 * @see org.xsocket.connection.IConnectHandler#onConnect(org.xsocket.connection.INonBlockingConnection)
	 */
	@Override
	public boolean onConnect( INonBlockingConnection con ) throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
		con = ConnectionUtils.synchronizedConnection( con );
		System.out.println( con.getRemoteAddress() + " " + con.getRemotePort() );
		con.write("hello");
		UserInfo user = new UserInfo();
		con.setAttachment( user );
		return false;
	}

	/* (non-Javadoc)
	 * @see org.xsocket.connection.IDataHandler#onData(org.xsocket.connection.INonBlockingConnection)
	 */
	@Override
	public boolean onData( INonBlockingConnection con ) throws IOException,	BufferUnderflowException, ClosedChannelException, MaxReadSizeExceededException {
		System.out.println( con.getRemoteAddress() + " " + con.getRemotePort() + " onData" );
		con = ConnectionUtils.synchronizedConnection( con );
		byte	head = 0;
		byte	foot = 0;
		short 	packageNo = 0;
		short 	dataLength = 0;
		con.markReadPosition();
		byte data[] = null;
		try {
	         head = con.readByte();
	         dataLength = con.readShort();
	         
	         data = con.readBytesByLength( dataLength );
	         foot = con.readByte();
	         con.removeReadMark();

	      } catch (BufferUnderflowException bue) {
	         con.resetToReadMark();
	         return true;
	      }

	      if( !checkInputData( head, foot ) ){
	    	  //TODO 调用某个退出函数
	      }
	      gameLogic.process( con, packageNo, data );
	      return true;
	}
	
	/**
	 * 检测客户端所发送的首尾标识位是否正确
	 * @param head
	 * @param foot
	 * @return
	 * 		true		首尾包号正确
	 * 		false		错误
	 */
	private boolean checkInputData( byte head, byte foot ){
		if( head != BasePackage.HEAD || foot != BasePackage.FOOT ){
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.xsocket.connection.IDisconnectHandler#onDisconnect(org.xsocket.connection.INonBlockingConnection)
	 */
	@Override
	public boolean onDisconnect( INonBlockingConnection con ) throws IOException {
		con = ConnectionUtils.synchronizedConnection( con );
		System.out.println( con.getRemoteAddress() + " " + con.getRemotePort() + " onDisconnect" );
		return false;
	}

}
