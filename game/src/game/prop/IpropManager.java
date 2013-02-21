package game.prop;


import game.ITransformStream;
import util.ErrorCode;

public interface IpropManager extends ITransformStream {

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
	
	/**
	 * 检查所需的道具数量是否足够
	 * @param unit
	 * @return
	 */
	ErrorCode checkPropIsEnough( PropUnit unit );
	
}
