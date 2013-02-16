package game.partner;

import game.battle.skill.SkillTemplet;

/**
 * 伙伴 模板
 * @author Administrator
 * 2013-2-5 上午11:52:49
 */
public class PartnerTemplet {
	private String						name;
	private short						templetId;

	/**
	 * 血槽最大值
	 */
	private int							hpMax;
	
	/**
	 * sp最大值
	 */
	private int							spMax;
	
	/**
	 * 速度
	 */
	private int							speed;
	
	/**
	 * 物理攻击力
	 */
	private int							phyAttack;
	
	/**
	 * 物理防御力
	 */
	private int							phyDefend;
	
	/**
	 * 命中率
	 */
	private int							hitRate;			
	
	/**
	 * 闪避率
	 */
	private int							dodgeRate;	
	
	/**
	 * 暴击
	 */	
	private int							crit;
	
	/**
	 * 反暴击
	 */	
	private int							unCrit;
	
	/**
	 * 格挡
	 */
	private int							block;
	
	/**
	 * 反格挡
	 */
	private int							unBlock;
	
	/**
	 * 技能模板
	 */
	private SkillTemplet				skillTemplet;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getTempletId() {
		return templetId;
	}

	public void setTempletId(short templetId) {
		this.templetId = templetId;
	}

	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
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

	public int getPhyAttack() {
		return phyAttack;
	}

	public void setPhyAttack(int phyAttack) {
		this.phyAttack = phyAttack;
	}

	public int getPhyDefend() {
		return phyDefend;
	}

	public void setPhyDefend(int phyDefend) {
		this.phyDefend = phyDefend;
	}

	public int getHitRate() {
		return hitRate;
	}

	public void setHitRate(int hitRate) {
		this.hitRate = hitRate;
	}

	public int getDodgeRate() {
		return dodgeRate;
	}

	public void setDodgeRate(int dodgeRate) {
		this.dodgeRate = dodgeRate;
	}

	public int getCrit() {
		return crit;
	}

	public void setCrit(int crit) {
		this.crit = crit;
	}

	public int getUnCrit() {
		return unCrit;
	}

	public void setUnCrit(int unCrit) {
		this.unCrit = unCrit;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getUnBlock() {
		return unBlock;
	}

	public void setUnBlock(int unBlock) {
		this.unBlock = unBlock;
	}

	public SkillTemplet getSkillTemplet() {
		return skillTemplet;
	}

	public void setSkillTemplet(SkillTemplet skillTemplet) {
		this.skillTemplet = skillTemplet;
	}
	
	

}
