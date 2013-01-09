package game.mission.cfg;

import game.battle.auto.Formation9;
import game.battle.formation.IFormation;

import java.util.List;

public class MissionTemplet {

	private short				id;
	private String				name;
	private String				desc;
	private List<IFormation>	formations;
	
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
//	public List<IFormation> getFormations() {
//		return formations;
//	}
	/**
	 * 获取某一波的战士阵型的深度克隆数据
	 */
	public IFormation getFormationClone( int wave ){
		
		return new Formation9( formations.get( wave ) );
	}
	
	public void setFormations( List<IFormation> formations ) {
		this.formations = formations;
	}
	@Override
	public String toString() {
		return "MissionTemplet [id=" + id + ", name=" + name + ", desc=" + desc
				+ ", formations=" + formations + "]";
	}
	
	
}
