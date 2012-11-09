package game.battle;


public abstract class BaseBuff {

	protected boolean								isRemove = false;
	
	/**
	 * 持续时间，在回合制中指回合数
	 */
	protected int									duration;									
	
	/**
	 * 拥有此buff的玩家
	 */
	protected RunTimeFighter						runTimeFighter;
	
	/**
	 * 是否为减低buff，例如中毒效果
	 */
	protected boolean								isDeBuff;
	
	
	public BaseBuff( RunTimeFighter rFighter ) {
		this.runTimeFighter = rFighter;
	}
	
	/**
	 * 
	 * @param damage	外层计算的伤害
	 * @return			经过buff之后真正的伤害
	 */
	public abstract int run( BaseBattle battle, int damage );

//	/**
//	 * 开始buff，设置buff相关效果
//	 */
//	protected abstract void begin( );
//	
//	/**
//	 * 
//	 * 清除buff，还原buff效果
//	 */
//	protected abstract void end( );

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
	public void init() {
		// TODO Auto-generated method stub
		
	}

	
}
