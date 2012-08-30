package core;

import java.io.IOException;

import org.xsocket.connection.INonBlockingConnection;

public interface IGameLogic {

	/**
	 * 包处理器
	 * @param con
	 * @param packageNo		包号
	 * @param data			客户端传送的数据体，不包括包号，包长等信息
	 */
	void packageProcess( INonBlockingConnection con, short packageNo, byte[] data );
	
	/**
	 * 玩家关闭连接，退出游戏
	 * @param con
	 */
	void exit( INonBlockingConnection con ) throws IOException;

}
