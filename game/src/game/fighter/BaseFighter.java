package game.fighter;

import game.prop.EquipmentManager;
import game.prop.IEquipment;
import game.prop.PropDataManager;
import game.prop.templet.EquipmentTemplet;
import util.ErrorCode;

public class BaseFighter implements IFighter {

	/**
	 * 等级
	 */
	short			level;
	
	/**
	 * 当前血量
	 */
	int				hp;
	
	/**
	 * 血槽最大值
	 */
	int				hpMax;
	
	/**
	 * 当前sp
	 */
	int				sp;
	
	/**
	 * sp最大值
	 */
	int				spMax;
	
	/**
	 * 速度
	 */
	int				speed;
	
	@Override
	public ErrorCode dress(long oldPropId, long newPropId) {
//		
//		IEquipment equipment = EquipmentManager.getEquipmentById( newPropId );
//		if( equipment == null ){
//			return ErrorCode.PROP_NOT_FOUNTD;
//		}
//		
//		if( canDress( equipment ) == ErrorCode.SUCCESS ){
//			
//		}
		synchronized ( PropDataManager.class ) {
			
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 测试是否可以装备此道具
	 * @param equipment
	 * @return
	 */
	private ErrorCode canDress( IEquipment equipment ){
		EquipmentTemplet t = (EquipmentTemplet) equipment.getTemplet();
		if( t.getRequiredLevel() > getLevel() ){
			return ErrorCode.FIGHTER_LEVEL_NOT_ENOUGH;
		}
		
		//其他检测
		return ErrorCode.SUCCESS;
	}

	@Override
	public short getLevel() {
		return level;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}

	public int getSp() {
		return sp;
	}

	public void setSp(int sp) {
		this.sp = sp;
	}

	public int getSpMax() {
		return spMax;
	}

	public void setSpMax(int spMax) {
		this.spMax = spMax;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	
}
