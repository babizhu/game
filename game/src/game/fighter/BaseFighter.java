package game.fighter;

import game.battle.auto.buff.BuffManager;
import game.battle.skill.SkillTemplet;
import game.prop.EquipmentManager;
import game.prop.IEquipment;
import game.prop.templet.EquipmentTemplet;
import util.ErrorCode;

public class BaseFighter implements IFighter {

	/**
	 * 名字
	 */
	String						name;
	
	/**
	 * 所在阵型中的位置
	 */
	byte						position;
	
	/**
	 * 等级
	 */
	short						level = 1;
	
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
	 * 物理防御力
	 */
	int							phyDefend;
	
	/**
	 * 命中率
	 */
	int							hitRate;			
	
	/**
	 * 闪避率
	 */
	int							dodgeRate;	
	
	/**
	 * 暴击
	 */	
	int							crit;
	
	/**
	 * 反暴击
	 */	
	int							unCrit;
	
	/**
	 * 格挡
	 */
	int							block;
	
	/**
	 * 反格挡
	 */
	int							unBlock;
	
	/**
	 * 技能模板
	 */
	SkillTemplet				skillTemplet;
	
	
	/**
	 * 是否允许出招
	 */
	private boolean 			isCanHit		= true;
	
	/*
	 * 是否处于战场的左边
	 */
	private boolean				isLeft			= true;
	
	private BuffManager			buffManager;

	/**
	 * 每次开战前必须进行的初始化工作
	 * @param battle
	 */
	public void initForBattle(){
		buffManager = new BuffManager();
	}
	
	/**
	 * 拷贝构造函数，通常用于战斗前的准备工作
	 * @param f
	 */
	public BaseFighter( BaseFighter f ) {
		position = f.position;
		level = f.level;
		hp = f.hp;
		hpMax = f.hpMax;
		sp = f.sp;
		spMax = f.spMax;
		speed = f.speed;
		phyAttack = f.phyAttack;
		phyDefend = f.phyDefend;
		hitRate = f.hitRate;
		dodgeRate = f.dodgeRate;
		crit = f.crit;
		unCrit = f.unCrit;
		block = f.block;
		unBlock = f.unBlock;
		skillTemplet = f.skillTemplet;
		name = f.name;
		isLeft = f.isLeft;

	}

	public BaseFighter() {
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
	 * 物防
	 * @return
	 */
	public int getPhyDefend() {
		return phyDefend;
	}

	/**
	 * 战士是否位于战场左边
	 * @return
	 */
	public boolean isLeft() {
		return isLeft;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
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

	

	public void setHitRate(int hitRate) {
		this.hitRate = hitRate;
	}

	public void setDodgeRate(int dodgeRate) {
		this.dodgeRate = dodgeRate;
	}

	/**
	 * 命中率
	 * @return
	 */
	public int getHitRate() {
		return hitRate;
	}

	/**
	 * 闪避率
	 * @return
	 */
	public int getDodgeRate() {
		return dodgeRate;
	}

	

	/**
	 * 暴击
	 * @return
	 */
	public int getCrit() {
		return crit;
	}

	/**
	 * 反暴击
	 * @return
	 */
	public int getUnCrit() {
		return unCrit;
	}

	/**
	 * 格挡
	 * @return
	 */
	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	/**
	 * 反格挡
	 * @return
	 */
	public int getUnBlock() {
		return unBlock;
	}

	public void setUnBlock(int unBlock) {
		this.unBlock = unBlock;
	}

	public void setPhyDefend(int phyDefend) {
		this.phyDefend = phyDefend;
	}

	public void setCrit(int crit) {
		this.crit = crit;
	}

	public void setUnCrit(int unCrit) {
		this.unCrit = unCrit;
	}

	public boolean isDie() {
		return hp <= 0;
	}

	public SkillTemplet getSkillTemplet() {
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSkillTemplet(SkillTemplet skillTemplet) {
		this.skillTemplet = skillTemplet;
	}

	@Override
	public String toString() {
		return "BaseFighter [name=" + name + ", position=" + position
				+ ", level=" + level + ", hp=" + hp + ", hpMax=" + hpMax
				+ ", sp=" + sp + ", spMax=" + spMax + ", speed=" + speed
				+ ", phyAttack=" + phyAttack + ", phyDefend=" + phyDefend
				+ ", hitRate=" + hitRate + ", dodgeRate=" + dodgeRate
				+ ", crit=" + crit + ", unCrit=" + unCrit + ", block=" + block
				+ ", unBlock=" + unBlock + ", skillTemplet=" + skillTemplet
				+ ", isCanHit=" + isCanHit + ", isLeft=" + isLeft + "]\r\n";
	}

	public String toSimpleString() {
		return "name=" + name + ", position=" + position;
		
	}


	
	
}
