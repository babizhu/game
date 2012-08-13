package game.packages;

import game.util.PackageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 包管理器<br>
 * 单例
 * 整个系统使用一个实例
 * @author liukun
 *
 */
public class PackageManager {

	private final static PackageManager		instance = new PackageManager();
	public  final static PackageManager 	getInstance(){ return instance; }
	
	/**
	 * 包程序所在的目录
	 */
	private	final static String				PACKAGE_PATH = "game.packages";
	
	/**
	 * 系统允许最大的包号，用于生成数组存放所有的包实例
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
						System.out.println(packetNo + " 重复了");
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}				
			}
		}
				
	}
	public static void main ( String[] args ) {
		PackageUtil.printAllPakcets( PackageManager.getInstance().packages );
	}
	
	
	
	
	
	
}
