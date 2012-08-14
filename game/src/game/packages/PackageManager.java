package game.packages;

import game.util.PackageUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import user.UserInfo;
import util.ErrorCode;

/**
 * 包管理器<br>
 * 单例
 * 整个系统使用一个实例
 * @author liukun
 *
 */
public class PackageManager {

	private final static Logger logger = LoggerFactory.getLogger( PackageManager.class ); 
	private final static PackageManager		instance = new PackageManager();
	public  final static PackageManager 	getInstance(){ return instance; }
	
	/**
	 * 包程序所在的目录
	 */
	private	final static String				PACKAGE_PATH = "game.packages";
	
	/**
	 * 系统允许最大的包号，用于生成数组存放所有的包实例，包号的生成得稍微限制一下，最好不要超过10000，否则会开一个比较大的数组
	 */
	private	final static int				MAX_PACKAGE_NO = 1000;
	
	/**
	 * 程序内所有的包实例列表
	 */
	private final BasePackage[]				packages;
	
	private PackageManager() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list = PackageUtil.getClasses( PACKAGE_PATH );
		packages = new BasePackage[MAX_PACKAGE_NO];// 不存在0号包
		
		for( Class<?> c : list ) {
			if( !c.isInterface() && !c.getName().contains("Base") && !c.getName().contains("Manager") ) {
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
		
		pack.run( user, buf );
		return ErrorCode.SUCCESS;
		
	}
	public static void main ( String[] args ) {
		PackageUtil.printAllPakcets( PackageManager.getInstance().packages );
	}
	
}
