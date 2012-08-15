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
 * @author liukun
 * 
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
	 * 程序内所有的包实例数组
	 */
	private final static BasePackage[]				packages;
	
	/**
	 * 接收相同包号两个包之间允许的最短时间间隔，如果小于这个值则认定客户端有刷包嫌疑，丢弃这个包
	 */
	private static final long MIN_INTERVAL_MILS = 0;
	
	/**
	 * 初始化系统所有的包
	 */
	static{
		List<Class<?>> list = PackageUtil.getClasses( PACKAGE_PATH );
		packages = new BasePackage[MAX_PACKAGE_NO];// 不存在0号包
		
		for( Class<?> c : list ) {
			if( !c.isInterface()/* && !c.getName().contains("Base") && !c.getName().contains("Manager")*/ ) {
//			if( c.isInstance( BasePackage.class ) ){

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
	 * 上一次包号
	 */
	private short	lastPackageNo = 0;
	
	/**
	 * 上一次收报时间
	 */
	private	long	lastReceiveTime = 0;
	
	PackageManager() {
				
	}
	
	/**
	 * 客户端发送数据到服务器端，等待服务器端进行处理
	 * @param packageNo
	 * @param user
	 * @param buf
	 * @return
	 */
	public ErrorCode run( short packageNo, UserInfo user, ByteBuffer buf ){
		BasePackage pack = packages[packageNo];
		if( pack == null ){
			logger.info( "package No." + packageNo + " NOT FOUND！" );
			return ErrorCode.PACAKAGE_NOT_FOUND;
		}
		
		if( !securityCheck( packageNo ) ){
			return ErrorCode.PACKAGE_SECURITY_CHECK_FAIL;
		}
		pack.run( user, buf );
		return ErrorCode.SUCCESS;		
	}
	
	/**
	 * 检查玩家是否存在段时间内恶意刷大量包的情况
	 * @param packageNo
	 * @return
	 */
	private boolean securityCheck( short packageNo ){
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
