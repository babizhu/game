package game.battle.buff;

import game.battle.buff.templet.BuffTempletBase;
import game.fighter.FighterBase;


public abstract class BuffBase {

	protected boolean								isRemove = false;
	
	BuffTempletBase									templet;
	
	/**
	 * 拥有此buff的玩家
	 */
	protected FighterBase							receiver;
	
	/**
	 * 发送buff的人
	 */
	protected FighterBase							sender;
	
	
	
	
	public BuffBase( FighterBase receiver, FighterBase sender ) {
		this.receiver = receiver;
		this.sender = sender;
	}
	
	/**
	 * 
	 * @param damage	外层计算的伤害
	 * @return			经过buff之后真正的伤害
	 */
	public abstract int run( int damage );


	/**
	 * 获取该buff的运行时间点
	 * @return
	 */
	public abstract BuffRunPoint getBuffRunPoint();

	/**
	 * 如果持续回合数到0，或者此buff被另外的方式强制清除，皆返回已经移除
	 * @return
	 */
	public boolean isRemove() {
		return isRemove;
	}

	/**
	 * 通常用于主动消除此buff的效果，慎用！
	 * @param isRemove
	 */
	public void setRemove( boolean isRemove ) {
		this.isRemove = isRemove;
	}

	/**
	 * 有必要的话，进行相关设置
	 */
	public void start() {
		
	}
	
	/**
	 * 有必要的话，进行相关设置
	 */
	protected void stop() {
		isRemove = true;
	}
	
	
}
