package game.mission.cfg;

import game.battle.formation.IFormation;

public class MissionTemplet {

	short				id;
	String				name;
	String				desc;
	IFormation[]		formations = new IFormation[3];
	public short getId() {
		return id;
	}
	public void setId(short id) {
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
	public IFormation[] getFormations() {
		return formations;
	}
	public void setFormations(IFormation[] formations) {
		this.formations = formations;
	}
	
	
}
