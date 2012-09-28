package game.bag;

import game.prop.IProp;

import java.util.Set;


/**
 * 背包系统<br>
 * 系统需求<br>
 * 每个玩家可以拥有多个背包，每个背包可拥有多个格子
 * 
 * @author liukun
 * 2012-9-27 下午04:50:19
 */
public interface IBag {
	
	/**
	 * 获取count个类型为typeid的道具，并且从格子中删除它
	 * 不存在返回null
	 * @param unit		移除的内容
	 * @return
	 * 有变化的格子列表
	 * 
	 */
	Set<IGrid> remove( PropUnit unit );
	
	/**
	 * 批量删除道具，通常用于合成物品，必须保证所有的材料都存在的时候才开始扣除，有如下约定：<br>
	 * 1、要么扣完，要么不扣<br>
	 * 2、返回null，意味扣除失败<br>
	 * 
	 * @param units		为一个数组，内容是一个typeid，一个数量，typeid，一个数量，循环
	 * @return
	 * 有变化的格子列表
	 * 
	 */
	Set<IGrid> remove( PropUnit[] units  );

	
	Set<IGrid> put( PropUnit unit );
	
	/**
	 * 整理格子
	 */
	void tidy();

}
