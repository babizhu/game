package game.mission.cfg;

import game.battle.auto.Formation9;
import game.battle.formation.IFormation;

import java.util.List;

public class MissionTemplet {

	private final short					id;
	private final String				name;
	private final String				desc;
	private final List<IFormation>		formations;
	
	public short getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return desc;
	}
	
//	public List<IFormation> getFormations() {
//		return formations;
//	}
	/**
	 * 获取某一波的战士阵型的深度克隆数据，波数从0开始
	 */
	public IFormation getFormationCloneByWave( int wave ){
		
		return new Formation9( formations.get( wave ) );
	}
	
	public MissionTemplet(short id, String name, String desc,
			List<IFormation> formations) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.formations = formations;
	}

	@Override
	public String toString() {
		return "MissionTemplet [id=" + id + ", name=" + name + ", desc=" + desc
				+ ", formations=" + formations + "]";
	}
	
	
}
