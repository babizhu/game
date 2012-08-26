package game.packages;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import user.UserInfo;
import util.ErrorCode;
import util.SystemTimer;

/**
 * 包管理器
 * 
 * @author admin
 * 2012-8-15 上午11:50:42
 */
public class PackageManager {

	private final static Logger 			logger = LoggerFactory.getLogger( PackageManager.class ); 

	/**
	 * 接收相同包号两个包之间允许的最短时间间隔，如果小于这个值则认定客户端有刷包嫌疑，丢弃这个包
	 * 单位	毫秒
	 */
	private static final long 				MIN_INTERVAL_MILS = 0;
	

	/**
	 * 接收的上一个包
	 */
	private Packages	lastPack;
	
	/**
	 * 上一次收包时间
	 */
	private	long	lastReceiveTime = 0;
	
	public PackageManager() {
	}
	
	/**
	 * 客户端发送数据到服务器端，等待服务器端进行处理
	 * @param user			玩家
	 * @param packageNo		包号
	 * @param buf			消息正文
	 * @return
	 */
	public ErrorCode run( UserInfo user, Packages pack, ByteBuffer buf ){
		
		if( !safeCheck( pack ) ){
			return ErrorCode.PACKAGE_SAFE_CHECK_FAIL;
		}
		try{
			pack.run( user, buf );
		}
		catch( Exception e ){
			logger.debug( user.getName() + "," + pack + "," + buf, e );
		}
		return ErrorCode.SUCCESS;	
	}
	
	/**
	 * 检查玩家是否存在断时间内恶意刷大量包的情况
	 * @param packageNo
	 * @return
	 */
	private boolean safeCheck( Packages pack ){
		long current = SystemTimer.currentTimeMillis();
		if( pack == lastPack && current - lastReceiveTime < MIN_INTERVAL_MILS ){
			return false;
		}
		lastPack = pack;
		lastReceiveTime = current;
		return true;
	}
	public static void main ( String[] args ) {
		//PackageUtil.printAllPakcets( PackageManager.packages );
	}	
}
