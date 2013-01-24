package core;

import game.events.EventManager;
import game.events.all.UserCreateEvent;
import game.events.all.UserLoginEvent;


import java.io.IOException;
import java.nio.ByteBuffer;

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
	 * @throws IOException
	 */
	@Override
	public void packageRun( INonBlockingConnection con, short packageNo, byte[] data ) throws IOException {
		
		EventManager pack = EventManager.fromNum( packageNo );

		ErrorCode code = ErrorCode.SUCCESS;

		String name = (String) con.getAttachment();
		logger.debug( (buildPrefixStr( con, name ) + "通信包：" + (pack == null ? packageNo : pack)) );
		if( pack == null ) {
			code = ErrorCode.PACKAGE_NOT_FOUND;
		} else {
			try{
				ByteBuffer buf = ByteBuffer.wrap( data );
				if( pack == EventManager.USER_LOGIN ){
					if( name != null ){
						code = ErrorCode.USER_HAS_LOGIN;
					}
					else{
						UserLoginEvent p = (UserLoginEvent) EventManager.USER_LOGIN.getEventInstance();
						p.run( con, buf );
					}
					
				}
				else if( pack == EventManager.USER_CREATE ){
					if( name != null ){
						code = ErrorCode.USER_HAS_LOGIN;
					}
					else{
						UserCreateEvent p = (UserCreateEvent) EventManager.USER_CREATE.getEventInstance();
						p.run( con, buf );
					}
				}
				else{
					if( name == null ){
						code = ErrorCode.USER_NOT_LOGIN;
					}
					else{
						code = UserManager.getInstance().packageRun( name, pack, data );
					}
				}
			}
			catch( Exception e ){
				logger.debug( buildPrefixStr( con, name ) + "包执行异常：" , e );
			}
		}

		if (code != ErrorCode.SUCCESS) {
		
			logger.debug( buildPrefixStr( con, name ) + "错误码:[" + code + "] 包:" + pack + "[" + packageNo + "] " + name );
		}
	}
	
	/**
	 * 针对此类，提供一个统一的提示信息前缀
	 * @return
	 */
	private String buildPrefixStr( INonBlockingConnection con, String name ){
		String s = name;
		s += "[";
		s += con.getId().substring( 21 );
		s += ",";
		s += con.getRemoteAddress();
		s += "] ";
		return s;
	}

	/**
	 * 玩家关闭连接，退出游戏,这里无需考虑网络层的代码，只需考虑user层
	 * 
	 */
	@Override
	public void exit( INonBlockingConnection con ) throws IOException {
		String name = (String) con.getAttachment();

		if( name != null ){
			System.out.println( buildPrefixStr( con, name ) + "执行退出程序");
			
			ErrorCode code = UserManager.getInstance().exit( name );
			if (code != ErrorCode.SUCCESS) {
				logger.debug( "用户退出发生错误：" + name + "[" + con.getId() + "], 错误码:" + code );
			}
		}

		
	}
}
