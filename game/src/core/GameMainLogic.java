package core;

import game.packages.Packages;
import game.packages.packs.SystemSendErrorCodePackage;

import java.io.IOException;
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
	 *            去除包头，包尾，包号，包长的附加信息的数据
	 * 
	 * <br>
	 *            本函数原则上不主动发送包，函数尾部有个发送的错误信息包目前只用于用例测试，正式发布的时候应考虑删除<br>
	 *            具体情况请查看{@link game.packages.packs.UserLoginPackageTest#Login}
	 *            
	 *  @注意：原本设计在这里对user进行加锁，保证同步，后来发现这样在交互类操作（精英挑战赛）会导致死锁，具体查看
	 *  {@link game.packages.packs.DeadLockTestPackageTest#testDeadLock}
	 * @throws IOException
	 */
	@Override
	public void packageProcess(INonBlockingConnection con, short packageNo,
			byte[] data) throws IOException {
		
		Packages pack = Packages.fromNum(packageNo);
		ErrorCode code;
		UserInfo user = (UserInfo) con.getAttachment();
		if (pack == null) {
			code = ErrorCode.PACKAGE_NOT_FOUND;
		} else {
/*
			

			if (user.getStatus() == UserStatus.LOGIN) {
				if (pack == Packages.USER_LOGIN || pack == Packages.USER_CREATE) {
					code = ErrorCode.USER_HAS_LOGIN;
				} else {
					code = user.getPackageManager().run(user, pack, buf);
				}
			} else if (user.getStatus() == UserStatus.GUEST) {
				if (pack != Packages.USER_LOGIN && pack != Packages.USER_CREATE) {
					code = ErrorCode.USER_NOT_LOGIN;
				} else {
					code = user.getPackageManager().run(user, pack, buf);
				}
			} else {
				code = ErrorCode.UNKNOW_ERROR;
			}
*/
			if( (pack == Packages.USER_LOGIN || pack == Packages.USER_CREATE) && user.getStatus() == UserStatus.LOGIN ) {
				code = ErrorCode.USER_HAS_LOGIN;
			}
			else if( (pack != Packages.USER_LOGIN && pack != Packages.USER_CREATE) && user.getStatus() == UserStatus.GUEST ) {
				code = ErrorCode.USER_NOT_LOGIN;
			}
			else{
				ByteBuffer buf = ByteBuffer.wrap( data );
				code = user.getPackageManager().run( user, pack, buf );
			}
		}
		
		if (code != ErrorCode.SUCCESS) {
			SystemSendErrorCodePackage p = (SystemSendErrorCodePackage) Packages.SYSTEM_SEND_ERROR_CODE
					.getPackageInstance();
			p.run(user, code);
			// TODO DEBUG:整个try块似乎只用于用例测试，正式发布的时候可以考虑删除
			logger.debug("错误码:" + code + " 包号:" + packageNo + " " + user);
			// TODO 断开连接？
		}
	}

	/**
	 * 玩家关闭连接，退出游戏
	 */
	@Override
	public void exit(INonBlockingConnection con) throws IOException {
		// UserInfo user = (UserInfo) con.getAttachment();
		// ErrorCode code;
		// synchronized (user) {
		// code = UserManager.getInstance().exit( user );
		// }
		// if (code != ErrorCode.SUCCESS) {
		// logger.debug( user.getName() + "[" + con.getId() + "], 错误码:" + code
		// );
		//
		// //TODO 断开连接？
		// }
	}
}
