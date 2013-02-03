package game.prop;

import util.ErrorCode;

public interface IpropManager {

	ErrorCode add( PropUnit unit );
	ErrorCode remove( PropUnit unit );
	
	/**
	 * 计算放入当前物品之后，额外所需要的格子数，不包括原来所占有的格子
	 * @param unit		要放入的物品
	 * @return
	 */
	int calcNeedGridCount( PropUnit unit );
	
	/**
	 * 当前占用了多少个格子
	 * @return
	 */
	int getGridCount();
}
