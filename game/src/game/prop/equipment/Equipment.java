package game.prop.equipment;

import game.prop.Gem;
import game.prop.ICalculateAddtion;
import game.prop.PropBase;
import game.prop.templet.EquipmentTemplet;
import game.prop.templet.PropTempletBase;

import java.util.Arrays;

import util.ErrorCode;


/**
 * 通常指可穿戴或配置的装备类，并且必须在相应位置出现的，比如刀只能放在武器的位置上
 * @author liukun
 * 2013-1-31 下午4:30:31
 */
public class Equipment extends PropBase implements IEquipment, ICalculateAddtion{
	private final EquipmentTemplet		templet;
	private long						id;
	private short						level = 1;
	private Gem[]						gems = new Gem[0];
	
	
	public Equipment(PropTempletBase templet ) {
		super();
		this.templet = (EquipmentTemplet) templet;
	}

	public EquipmentTemplet getTemplet() {
		return templet;
	}

	public long getId() {
		return id;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	/**
	 * 获取用逗号分割的宝石id字符串用于数据库存储
	 * @return
	 */
	public String getGemStr() {
		if( gems.length == 0 ){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for( Gem g : gems ){
			sb.append( g.getId() );
			sb.append( "," );
		}
		return sb.substring( 0, sb.length() - 1 );
	}

	public Gem[] getGem() {
		return gems;
	}

	public void setGem( String str ) {
		String[] arr = str.split( "," );
		gems = new Gem[arr.length];
		for( int i = 0; i < arr.length; i++ ){
			Gem g = new Gem();
			gems[i] = g;
		}
	}

	public void setId(long id) {
		this.id = id;
	}

	
	@Override
	public String toString() {
		return "Equipment [templet=" + templet.getTempletId() + ", id=" + id + ", level="
				+ level + ", gems=" + Arrays.toString(gems) + "]";
	}

	@Override
	public ErrorCode levelUp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buildContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calcAddtion() {
		// TODO Auto-generated method stub
		
	}

	
	
}
