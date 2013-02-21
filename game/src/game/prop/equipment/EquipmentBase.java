package game.prop.equipment;

import game.prop.ICalculateAddtion;
import game.prop.PropBase;

import java.nio.ByteBuffer;

import util.ErrorCode;


/**
 * 通常指可穿戴或配置的装备类，并且必须在相应位置出现的，比如刀只能放在武器的位置上
 * @author liukun
 * 2013-1-31 下午4:30:31
 */
public abstract class EquipmentBase extends PropBase implements ICalculateAddtion{
	private long						id;
	private short						level = 1;
	
	/**
	 * 装备升级，参数会根据实际情况进行改变
	 * @return
	 */
	public abstract ErrorCode levelUp();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	@Override
	public void buildTransformStream( ByteBuffer buf ) {
		super.buildTransformStream(buf);
		buf.putLong( id );
		buf.putShort( level );
	}
	
	@Override
	public String toString() {
		return " [templet=" + getTemplet().getId() + ", id=" + getId() + ", level="
				+ getLevel();
	}
	
}
