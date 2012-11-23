package game.fighter;

import game.battle.BaseBattle;
import game.battle.auto.buff.BuffManager;
import game.prop.EquipmentManager;
import game.prop.IEquipment;
import game.prop.templet.EquipmentTemplet;
import util.ErrorCode;

public class BaseFighter implements IFighter {

	/**
	 * 所在阵型中的位置
	 */
	byte						position;
	
	/**
	 * 等级
	 */
	short						level;
	
	/**
	 * 当前血量
	 */
	int							hp;
	
	/**
	 * 血槽最大值
	 */
	int							hpMax;
	
	/**
	 * 当前sp
	 */
	int							sp;
	
	/**
	 * sp最大值
	 */
	int							spMax;
	
	/**
	 * 速度
	 */
	int							speed;
	
	/**
	 * 物理攻击力
	 */
	int							phyAttack;
	
	/**
	 * 是否允许出招
	 */
	private boolean 			isCanHit;
	
	/*
	 * 是否处于战场的左边
	 */
	private boolean				isLeft;
	
	private BuffManager			buffManager;
	
	/**
	 * 拷贝构造函数，通常用于战斗前的准备工作
	 * @param f
	 */
	public BaseFighter( BaseFighter f, boolean isLeft ) {
		position = f.position;
		level = f.level;
		hp = f.hp;
		hpMax = f.hpMax;
		sp = f.sp;
		spMax = f.spMax;
		speed = f.speed;
		phyAttack = f.phyAttack;
		isCanHit = true;
		buffManager = new BuffManager();
		this.isLeft = isLeft;
		
	}

	public BaseFighter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ErrorCode dress(long oldPropId, long newPropId) {
		
		
		IEquipment equipment = EquipmentManager.getEquipmentById( newPropId );
		if( equipment == null ){
			return ErrorCode.PROP_NOT_FOUNTD;
		}
		
		if( canDress( equipment ) == ErrorCode.SUCCESS ){
			
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

	public int getPhyAttack() {
		return phyAttack;
	}

	public void setPhyAttack( int phyAttack ) {
		this.phyAttack = phyAttack;
	}

	public BuffManager getBm() {
		return buffManager;
	}

	/**
	 * 战士是否位于战场左边
	 * @return
	 */
	public boolean isLeft() {
		return isLeft;
	}

	public void setCanHit(boolean isCanHit) {
		this.isCanHit = isCanHit;
	}

	public boolean isCanHit() {
		return isCanHit;
	}

	public byte getPosition() {
		return position;
	}

	public void setPosition( byte position ) {
		this.position = position;
	}

	/**
	 * 每次开战前必须进行的初始化工作
	 * @param battle
	 */
	public void initForBattle( BaseBattle battle ){
		buffManager = new BuffManager();
		isCanHit = true;
		
	}
	@Override
	public String toString() {
		return "BaseFighter [position=" + position + ", level=" + level
				+ ", hp=" + hp + ", hpMax=" + hpMax + ", sp=" + sp + ", spMax="
				+ spMax + ", speed=" + speed + ", phyAttack=" + phyAttack
				+ ", isCanHit=" + isCanHit + ", isLeft=" + isLeft
				+ ", buffManager=" + buffManager + "]";
	}

	
	
}
