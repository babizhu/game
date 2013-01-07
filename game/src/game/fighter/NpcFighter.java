package game.fighter;

public class NpcFighter extends BaseFighter {


	private Short			id;
	private String			desc;
	
	public NpcFighter( NpcFighter npc ) {
		super( npc );
		desc = npc.desc;
		id = npc.id;
		initForBattle( false );
	}

	public NpcFighter() {
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "NpcFighter [id=" + id + ", desc=" + desc + ", " + super.toString().substring( 13 );
	}
	
	
	
}
