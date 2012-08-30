package core;

import game.packages.Packages;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;

import user.UserInfo;
import user.UserManager;
import user.UserStatus;
import util.ErrorCode;

/**
 * 游戏的主干框架 单例
 * 
 * @author liukun
 * 
 *         2012-8-16 下午04:13:27
 */
public class GameMainLogic implements IGameLogic {

	private final static Logger logger = LoggerFactory
			.getLogger(GameMainLogic.class);
	private static final GameMainLogic instance = new GameMainLogic();

	public static final GameMainLogic getInstance() {
		return instance;
	}

	private GameMainLogic() {
	}

	/**
	 * 处理玩家传送的包信息
	 * 
	 * @param con
	 * @param packageNo
	 * @param data			去除包头，包尾，包号，包长的附加信息的数据
	 */
	@Override
	public void packageProcess( INonBlockingConnection con, short packageNo, byte[] data) {
		Packages pack = Packages.fromNum(packageNo);
		ErrorCode code;
		UserInfo user = (UserInfo) con.getAttachment();
		synchronized (user) {
			if (pack == null) {
				code = ErrorCode.PACKAGE_NOT_FOUND;
			} else {
				
				ByteBuffer buf = ByteBuffer.wrap(data);
//				if( user.getStatus() == UserStatus.NEW && pack != Packages.USER_CREATE ){
//					code = ErrorCode.USER_NOT_FOUND;
//				}
//				else if( user.getStatus() == UserStatus.GUEST && pack != Packages.USER_LOGIN ){
//					code = ErrorCode.USER_NOT_LOGIN;
//				} 
//				else if( user.getStatus() == UserStatus.LOGIN ){
//					code = user.getPackageManager().run(user, pack, buf);
//				}
//				else{
//					
//					
//				}
				if( pack == Packages.USER_CREATE && user.getStatus() != UserStatus.NEW ){
					code = null;
				}
				
			}
			if (code != ErrorCode.SUCCESS) {
				logger.debug( "错误码:" + code + " 包号: " + packageNo + user + "[" + con.getId() + "]" );
				
				//TODO 断开连接？
			}
		}
	}

	/**
	 * 玩家关闭连接，退出游戏
	 */
	@Override
	public void exit(INonBlockingConnection con) throws IOException {
		UserInfo user = (UserInfo) con.getAttachment();
		ErrorCode code;
		synchronized (user) {
			code = UserManager.getInstance().exit( user );
		}	
		if (code != ErrorCode.SUCCESS) {
			logger.debug( user.getName() + "[" + con.getId() + "], 错误码:" + code );
			
			//TODO 断开连接？
		}
	}
}
