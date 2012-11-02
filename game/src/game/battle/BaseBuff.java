package game.battle;

import game.BuffRunPoint;
import game.fighter.BaseFighter;

public abstract class BaseBuff {
	

	protected boolean							isRemove = false;
	protected BaseBattle						battle;
	protected BuffRunPoint						point;
	
	
	/**
	 * 拥有此buff的玩家
	 */
	BaseFighter				fighter;
	
	public BaseBuff( BaseBattle battle, BaseFighter fighter ) {
		this.battle = battle;
		this.fighter = fighter;
	}
	
	/**
	 * 
	 * @param damage	外层计算的伤害
	 * @return
	 * 		经过buff运算后产生的真正的伤害
	 */
	public abstract float run( float damage );

	/**
	 * 开始buff，设置buff相关效果
	 */
	protected abstract void begin( );
	
	/**
	 * 
	 * 清除buff，还原buff效果
	 */
	protected abstract void end( );


}
