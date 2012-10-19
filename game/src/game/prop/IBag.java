package game.prop;

import game.bag.PropUnit;
import util.ErrorCode;

/**
 * 背包系统<br>
 * 系统需求<br>
 * 每个玩家可以拥有多个背包，每个背包可拥有多个格子
 * 
 * 同步的思考：
 * 1、最好不要不同的背包之间互相交换物品，如果一定要这么做，请注意死锁发生的可能性，最好先加锁hash code比较小的背包
 * 2、要把背包内的物品（比如道具）直接交换到玩家身上，必须保证背包为最后一把锁，也就是说先加锁其他地方，在加锁背包，一定要保持这样的调用顺序
 * 
 * @author liukun
 * 2012-9-27 下午04:50:19
 */
public interface IBag {
	
	
	/**
	 * 从背包中移除count个类型为typeid的装备，或1个指定propId的道具<br><br>
	 * 特别考虑要移除的是装备，但是并未指定propId的情况(从背包中根据图纸合成某个道具)：<br>
	 * 这种情况，请设置unit的propId为0即可<br><br>
	 * 策略：要么全移除，要么全不移除<br>
	 * 
	 * @param 		unit		要移除的内容
	 * 
	 * @return
	 *			 有变化的格子列表
	 * 
	 */
	ErrorCode remove( PropUnit unit );
	
	/**
	 * 批量删除道具，通常用于合成物品，必须保证所有的材料都存在的时候才开始扣除<br>
	 * 策略：要么全移除，要么全不移除<br>
	 * 返回null，意味删除失败<br>
	 * 
	 * @param units		为一个数组
	 * @return
	 * 			有变化的格子列表
	 * 
	 */
	ErrorCode remove( PropUnit[] units  );

	/**
	 * 添加单个道具到背包<br>
	 * 策略：要么全放，要么全不放<br>
	 * 
	 * @param 		unit
	 * @return
	 */
	ErrorCode put( PropUnit unit );
	
	/**
	 * 添加多个道具到背包<br>
	 * 策略：要么全放，要么全不放<br>
	 * 
	 * @param 		units
	 * @return
	 * 			
	 */
	ErrorCode put( PropUnit[] units );
	
	/**
	 * 整理格子
	 
	void tidy();*/
	
	/**
	 * 把位于source位置的道具移动到dest，<br>
	 * 如果源和目的格子都有内容，除非道具的类型id形同，否则移动失败<br>
	 * 移动后，如果目的格子的上限超标，那么移动失败
	 * 
	 * 策略：要么全放，要么全不放
	 * @param source
	 * @param dest
	 
	ErrorCode exchange( short source, short dest );
*/

	

}
