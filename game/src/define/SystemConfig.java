package define;

import java.io.Serializable;

import util.SystemTimer;

/**
 * 保留系统信息，诸如启动时间，开服时间等一系列参数
 * @author admin
 *
 */
public class SystemConfig implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	public static SystemConfig getInstance(){
		return instance;
	}
	private static SystemConfig instance = new SystemConfig();
	private SystemConfig() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 系统本次启动时间
	 */
	public final static long START_MILS = SystemTimer.currentTimeMillis();
	

}
