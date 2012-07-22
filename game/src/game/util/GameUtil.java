package game.util;

import define.SystemConfig;

public class GameUtil {

	private static final long ID_PREFIX = SystemConfig.getGameDistrict() * 100000000000l;//����1000��
	/**
	 * ���ɴ�����Ϸ����Ϣ��ID
	 * @param id
	 * @return
	 */
	public static long buildIdWithDistrict( long id ){
		return ID_PREFIX + id;
	}
	public static void main(String[] args) {
		long begin = System.nanoTime();
		for( int i = 0; i < 100; i++ ){
			System.out.println( buildIdWithDistrict( i ) );
		}
		System.out.println( "��ʱ��" + (System.nanoTime() - begin) / 1000000000f );
	}
}
