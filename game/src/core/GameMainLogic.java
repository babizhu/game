package core;

import java.nio.ByteBuffer;

import user.UserInfo;
import user.UserStatus;

/**
 * 游戏的主干框架
 * @author liukun
 * 
 * 单例
 * 2012-8-16 下午04:13:27
 */
public class GameMainLogic {

	private static final GameMainLogic instance = new GameMainLogic();
	public static final GameMainLogic getInstance(){ 
		return instance;
	}
	
	private GameMainLogic(){		
	}
	
	
	/**
	 * 处理玩家传送过来的包信息
	 * @param user
	 * @param packageNo
	 * @param data
	 */
	public void process( UserInfo user, short packageNo, byte[] data ){
		
		if( user.getStatus() == UserStatus.GUEST && (packageNo != 100001 || packageNo != 100001) )
		{
			//TODO 玩家非法，移除此连接
		}
		ByteBuffer buf = ByteBuffer.wrap( data ); 
		user.getPackageManager().run( user, packageNo, buf );
		
	}
	

}
