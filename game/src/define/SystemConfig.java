package define;

import java.io.Serializable;

import util.SystemTimer;

/**
 * ����ϵͳ��Ϣ����������ʱ�䣬����ʱ���һϵ�в���
 * @author admin
 *
 */
public class SystemConfig implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ��Ϸ��id
	 */
	private static int gameDistrict = 1001;
	/**
	 * 
	 */
//	private static final long	serialVersionUID	= 1L;
//	public static SystemConfig getInstance(){
//		return instance;
//	}
//	private static SystemConfig instance = new SystemConfig();
//	private SystemConfig() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	
	/**
	 * ϵͳ��������ʱ��
	 */
	public final static long START_MILS = SystemTimer.currentTimeMillis();
	
	/**
	 * ������Ϸ����id
	 * @return
	 */
	public static int getGameDistrict(){
		return gameDistrict;
		
		
		
		
	}

}
