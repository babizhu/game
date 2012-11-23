package game.battle;

import game.fighter.BaseFighter;


public abstract class BaseBuff {

	protected boolean								isRemove = false;
	
	/**
	 * 持续时间，在回合制中指回合数
	 */
	protected int									duration;									
	
	/**
	 * 拥有此buff的玩家
	 */
	protected BaseFighter							self;
	
	/**
	 * 是否为减低buff，例如中毒效果
	 */
	protected boolean								isDeBuff = false;
	
	
	public BaseBuff( BaseFighter fighter ) {
		this.self = fighter;
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
	public void setRemove(boolean isRemove) {
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

	public boolean isDeBuff() {
		return isDeBuff;
	}


	
}
