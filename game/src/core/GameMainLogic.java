package core;

import game.packages.Packages;
import game.packages.packs.SystemSendErrorCodePackage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;

import user.UserManager;
import util.ErrorCode;

/**
 * 游戏的主干框架 单例
 * 
 * @author liukun
 * 
 *         2012-8-16 下午04:13:27
 */
public final class GameMainLogic implements IGameLogic {

	private static final  Logger logger = LoggerFactory.getLogger(GameMainLogic.class);
	private static final GameMainLogic instance = new GameMainLogic();

	public static final GameMainLogic getInstance() {
		return instance;
	}

	/**
	 * 处理客户端发送的包信息
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
	public void packageRun( INonBlockingConnection con, short packageNo, byte[] data ) throws IOException {
		
		Packages pack = Packages.fromNum( packageNo );
		ErrorCode code;
		String name = (String) con.getAttachment();
		if (pack == null) {
			code = ErrorCode.PACKAGE_NOT_FOUND;
		} else {			
			code = UserManager.getInstance().run( name, pack, data );
		}
		
		if (code != ErrorCode.SUCCESS) {
			SystemSendErrorCodePackage p = (SystemSendErrorCodePackage) Packages.SYSTEM_SEND_ERROR_CODE.getPackageInstance();
			p.run( con, code );
			logger.debug( "[" + con.getRemoteAddress() + "]错误码:[" + code + "] 包号:[" + pack + "] " + name );

			// TODO DEBUG:整个if块似乎只用于用例测试，正式发布的时候可以考虑删除
			// TODO 断开连接？
		}
	}

	/**
	 * 玩家关闭连接，退出游戏,这里无需考虑网络层的代码，只需考虑user层
	 * 
	 */
	@Override
	public void exit(INonBlockingConnection con) throws IOException {
		String name = (String) con.getAttachment();

		if( name != null ){
			ErrorCode code = UserManager.getInstance().exit( name );
			if (code != ErrorCode.SUCCESS) {
				logger.debug( "用户退出发生错误：" + name + "[" + con.getId() + "], 错误码:" + code );
			}

		}
	}
}
