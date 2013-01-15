package game.battle.skill;

import game.battle.formation.ChooseFighters;
import game.battle.formula.Formula;

import java.util.List;

public class SkillTemplet {
	private byte						id;
	private String						name;
	private String						desc;
	
	/**
	 * 对友的算法
	 */
	private List<SkillEffect>			effectOnFriend;
	
	/**
	 * 获取受技能影响的同伴的算法
	 */
	private ChooseFighters				friend;
	
	/**
	 * 对敌的算法
	 */
	private List<SkillEffect>			effectOnEnemy;
	
	/**
	 * 获取受技能影响的敌人的算法
	 */
	private ChooseFighters				enemy;
	
	/**
	 * 计算buff是否出现的概率公式
	 */
	private Formula						buffFormula;
	private byte						buffId;

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<SkillEffect> getEffectOnFriend() {
		return effectOnFriend;
	}

	public void setEffectOnFriend(List<SkillEffect> effectOnFriend) {
		this.effectOnFriend = effectOnFriend;
	}

	public ChooseFighters getFriends() {
		return friend;
	}

	public void setFriend(ChooseFighters friends) {
		this.friend = friends;
	}

	public List<SkillEffect> getEffectOnEnemy() {
		return effectOnEnemy;
	}

	public void setEffectOnEnemy(List<SkillEffect> effectOnEnemy) {
		this.effectOnEnemy = effectOnEnemy;
	}

	public ChooseFighters getEnemys() {
		return enemy;
	}

	public void setEnemy(ChooseFighters enemy) {
		this.enemy = enemy;
	}

	public Formula getBuffFormula() {
		return buffFormula;
	}

	public void setBuffFormula( Formula buffFormula ) {
		this.buffFormula = buffFormula;
	}

	public byte getBuffId() {
		return buffId;
	}

	public void setBuffId(byte buffId) {
		this.buffId = buffId;
	}

	@Override
	public String toString() {
		return "SkillTemplet [id=" + id + ", name=" + name + ", desc=" + desc
				+ ", effectOnFriend=" + effectOnFriend + ", friend=" + friend
				+ ", effectOnEnemy=" + effectOnEnemy + ", enemy=" + enemy
				+ ", buffFormula=" + buffFormula + ", buffId=" + buffId + "]";
	}
	public static void main(String[] args) {
		System.out.println( 23);
	}

}
