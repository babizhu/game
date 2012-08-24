package core;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;

import user.UserInfo;
import user.UserStatus;
import util.ErrorCode;

/**
 * 游戏的主干框架
 * 单例
 * @author liukun
 * 
 *  2012-8-16 下午04:13:27
 */
public class GameMainLogic implements IGameLogic{

	private final static Logger 			logger = LoggerFactory.getLogger( GameMainLogic.class ); 
	private static final GameMainLogic instance = new GameMainLogic();

	public static final GameMainLogic getInstance() {
		return instance;
	}

	private GameMainLogic() {
	}

	/**
	 * 处理玩家传送的包信息
	 * 
	 * @param user
	 * @param packageNo
	 * @param data
	 */
	@Override
	public void packageProcess(INonBlockingConnection con, short packageNo, byte[] data) {

		UserInfo user = (UserInfo) con.getAttachment();
		
		//TODO 基于玩家自身的同步似乎应该开始了
		if (user.getStatus() == UserStatus.GUEST
				&& (packageNo != 100001 || packageNo != 100001)) {
			// TODO 玩家非法，移除此连接
		}

		ByteBuffer buf = ByteBuffer.wrap(data);
		ErrorCode eCode = user.getPackageManager().run(user, packageNo, buf); 
		if( eCode != ErrorCode.SUCCESS ){
			logger.debug( user.getName() + ", 包号:" + packageNo + ", 错误码:" + eCode );
		}
	}
}
