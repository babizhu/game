package core;

import game.packages.Packages;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;

import user.UserInfo;
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
	 * @param data
	 */
	@Override
	public void packageProcess(INonBlockingConnection con, short packageNo,	byte[] data) {
		Packages pack = Packages.fromNum(packageNo);
		ErrorCode eCode;
		UserInfo user = (UserInfo) con.getAttachment();
		synchronized (user) {
			if (pack == null) {
				eCode = ErrorCode.PACKAGE_NOT_FOUND;//此处未同步是否有问题？
			} else {
				ByteBuffer buf = ByteBuffer.wrap(data);
				if (user.getStatus() == UserStatus.GUEST
						&& (pack != Packages.USER_LOGIN && pack != Packages.USER_CREATE )) {
					// TODO 玩家非法，移除此连接
					eCode = ErrorCode.USER_NOT_LOGIN;
				}
				else{
					eCode = user.getPackageManager().run(user, pack, buf);
				}

			}
			if (eCode != ErrorCode.SUCCESS) {
				logger.debug(user.getName() + ", 包号:" + packageNo + ", 错误码:"
						+ eCode);
			}
		}
	}
}
