package user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.SystemTimer;
import game.packages.PackageManager;
import game.packages.Packages;

/**
 * 检测玩家是否存在频繁发包的情况
 * 
 * @author liukun
 * 2012-9-11 下午05:06:47
 */
public class PackageCheck {

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
	
	/**
	 * 检查玩家是否存在断时间内恶意刷大量包的情况
	 * 只有连续2个相同的包才开启检测，例如两个登陆包的时间间隔小于MIN_INTERVAL_MILS，则被视为恶意刷包
	 * @param packageNo
	 * 
	 * @线程安全
	 * 
	 * @return
	 */
	public synchronized boolean safeCheck( Packages pack ){
		long current = SystemTimer.currentTimeMillis();
		if( pack == lastPack && current - lastReceiveTime < MIN_INTERVAL_MILS ){
			logger.debug( "packag:" + pack + current + "-" + lastReceiveTime  + "=" + (current - lastReceiveTime) );
			return false;
		}
		lastPack = pack;
		lastReceiveTime = current;
		return true;
	}

}
