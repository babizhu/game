package game.util;

import define.SystemConfig;

public class GameUtil {

	private static final long ID_PREFIX = SystemConfig.getGameDistrict() * 100000000000l;//

	/**
	 * 生成带有游戏区信息的ID
	 * 方法为：把配置表中的区id * 1000亿
	 * 
	 * 
	 * @param id
	 * @return
	 */
	public static long buildIdWithDistrict( long id ){
		if( id < ID_PREFIX ){
			return ID_PREFIX + id;
		}
		return id;
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		long begin = System.nanoTime();
		for( int i = 0; i < 100; i++ ){
			System.out.println( buildIdWithDistrict( i ) );
		}
		System.out.println( "耗时" + (System.nanoTime() - begin) / 1000000000f );
	}
}
