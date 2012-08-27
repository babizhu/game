
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

import user.UserInfo;
import core.GameMainLogic;

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
		con.available();//避免findbugs的警告
		//con.setIdleTimeoutMillis( 5000 );//连接上来之后，如果5秒不发包，主动切断
		return false;
		//return true;//不切断连接
	}
	
	@Override
	public boolean onConnect( INonBlockingConnection con ) throws IOException, BufferUnderflowException, MaxReadSizeExceededException {
		con = ConnectionUtils.synchronizedConnection( con );
		System.out.println( con.getId() + " onConnect" );

		UserInfo user = new UserInfo( con );
		con.setAttachment( user );
		return false;
	}

	@Override
	public boolean onData( INonBlockingConnection con ) throws IOException,	BufferUnderflowException, ClosedChannelException, MaxReadSizeExceededException {
		con = ConnectionUtils.synchronizedConnection( con );
		int available = con.available();
        if ( available > 0) {

        	System.out.println( con.getId() + " onData" + ",available:" + available + "byte" );
    		byte	head = 0;
    		byte	foot = 0;
    		short 	packageNo = 0;
    		short 	dataLength = 0;
    		con.markReadPosition();
			byte data[] = null;
			try {
				head = con.readByte();
				packageNo = con.readShort();
				dataLength = con.readShort();

				data = con.readBytesByLength(dataLength);
				foot = con.readByte();
				con.removeReadMark();

			} catch (BufferUnderflowException bue) {
				con.resetToReadMark();
				return true;
			}

			if (!checkInputData(head, foot)) {
				// TODO 调用某个退出函数
			}
			gameLogic.packageProcess( con, packageNo, data );
		}
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
		System.out.println( con.getId() + " onDisconnect" );
		return false;
	}
}
