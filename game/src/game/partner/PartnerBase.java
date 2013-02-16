package game.partner;

import game.fighter.BaseFighter;
import game.prop.equipment.Equipment;

import java.util.ArrayList;
import java.util.List;

public class PartnerBase extends BaseFighter{

	private final PartnerTemplet		templet;
	private long						id;
	private String						name;
	private byte						position;
	private short						level;
	private List<Equipment>				equipments = new ArrayList<Equipment>();
	
	
	public PartnerBase( PartnerTemplet templet) {
		this.templet = templet;
	}

	public PartnerTemplet getTemplet() {
		return templet;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte getPosition() {
		return position;
	}
	public void setPosition(byte position) {
		this.position = position;
	}
	public short getLevel() {
		return level;
	}
	public void setLevel(short level) {
		this.level = level;
	}
	
	/**
	 * 获取用逗号分割的装备id字符串用于数据库存储
	 * @return
	 */
	public String getEquipmentToStr() {
		if( equipments.size() == 0 ){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for( Equipment e : equipments ){
			sb.append( e.getId() );
			sb.append( "," );
		}
		return sb.substring( 0, sb.length() - 1 );
	}

	public void buildEquipment( String str ){
		
	}
	public void setEquipmentFromStr(String string) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
