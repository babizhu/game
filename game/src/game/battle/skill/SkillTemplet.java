package game.battle.skill;

import game.battle.formation.ChooseFighters;

import java.util.List;

public class SkillTemplet {
	private int							id;
	private String						name;
	private String						desc;
	
	/**
	 * 对友的算法
	 */
	private List<SkillEffect>			effectOnFriend;
	
	/**
	 * 获取受技能影响的同伴的算法
	 */
	private ChooseFighters				friends;
	
	/**
	 * 对敌的算法
	 */
	private List<SkillEffect>			effectOnEnemy;
	
	/**
	 * 获取受技能影响的敌人的算法
	 */
	private ChooseFighters				enemys;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
		return friends;
	}

	public void setFriends(ChooseFighters friends) {
		this.friends = friends;
	}

	public List<SkillEffect> getEffectOnEnemy() {
		return effectOnEnemy;
	}

	public void setEffectOnEnemy(List<SkillEffect> effectOnEnemy) {
		this.effectOnEnemy = effectOnEnemy;
	}

	public ChooseFighters getEnemys() {
		return enemys;
	}

	public void setEnemys(ChooseFighters enemys) {
		this.enemys = enemys;
	}
	
	

}
