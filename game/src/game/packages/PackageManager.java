package game.packages;

import game.util.PackageUtil;

import java.nio.ByteBuffer;
import java.util.List;

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
	 * 包程序文件所在的目录
	 */
	private	final static String				PACKAGE_PATH = "game.packages.packs";
	
	/**
	 * 系统允许最大的包号，用于生成数组存放所有的包实例，包号的生成得稍微限制一下，最好不要超过10000，否则会开一个比较大的数组
	 */
	private	final static int				MAX_PACKAGE_NO = 10000;
	
	/**
	 * 接收相同包号两个包之间允许的最短时间间隔，如果小于这个值则认定客户端有刷包嫌疑，丢弃这个包
	 * 单位	毫秒
	 */
	private static final long 				MIN_INTERVAL_MILS = 0;
	
	/**
	 * 程序内所有的包实例数组
	 */
	private final static BasePackage[]		packages;
	
	
	/**
	 * 初始化系统所有的包数组
	 */
	static{
		List<Class<?>> list = PackageUtil.getClasses( PACKAGE_PATH );
		packages = new BasePackage[MAX_PACKAGE_NO];// 不存在0号包
		
		for( Class<?> c : list ) {
			if( !c.isInterface() ) {
				BasePackage p;
				try {
					p = (BasePackage) c.newInstance();
					int packetNo = p.getPacketNo();
					if( packages[packetNo] == null ) {
						packages[packetNo] = p;

					} else {
						logger.error( packetNo + " 重复了，分别是" + packages[packetNo] + " " + p );
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}				
			}
		}
	}
	
	/**
	 * 接收的上一个包的包号
	 */
	private short	lastPackageNo = 0;
	
	/**
	 * 上一次收包时间
	 */
	private	long	lastReceiveTime = 0;
	
	public PackageManager() {
	}
	
	/**
	 * 客户端发送数据到服务器端，等待服务器端进行处理
	 * @param user
	 * @param packageNo
	 * @param buf
	 * @return
	 */
	public ErrorCode run( UserInfo user, short packageNo, ByteBuffer buf ){
		
		if( packageNo < 0 || packageNo > MAX_PACKAGE_NO ){
			return ErrorCode.PACKAGE_NOT_FOUND;
		}			
			
		BasePackage pack = packages[packageNo];
		if( pack == null ){
			return ErrorCode.PACKAGE_NOT_FOUND;
		}

		if( !safeCheck( packageNo ) ){
			return ErrorCode.PACKAGE_SAFE_CHECK_FAIL;
		}
		try{
			pack.run( user, buf );
		}
		catch( Exception e ){
//			e.printStackTrace();
			logger.debug( user.getName() + "," + packageNo + "," + buf, e );
//			logger.debug( e.getCause().toString() );
//			logger.debug( e.getLocalizedMessage() );
		}
		return ErrorCode.SUCCESS;	
	}
	
	/**
	 * 检查玩家是否存在断时间内恶意刷大量包的情况
	 * @param packageNo
	 * @return
	 */
	private boolean safeCheck( short packageNo ){
		long current = SystemTimer.currentTimeMillis();
		if( packageNo == lastPackageNo && current - lastReceiveTime < MIN_INTERVAL_MILS ){
			return false;
		}
		lastPackageNo = packageNo;
		lastReceiveTime = current;
		return true;
	}
	public static void main ( String[] args ) {
		PackageUtil.printAllPakcets( PackageManager.packages );
	}	
}
