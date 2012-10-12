package game.prop.cfg;

public abstract class BasePropTemplet {
	
	/**
	 * 道具类型id
	 */
	private int 		typeId;
	
	/**
	 * 道具名称
	 */
	private String		name;
	
	/**
	 * 使用所需等级
	 */
	private short		requiredLevel;
	
	/**
	 * 背包格子内叠加上限
	 */
	private short		stackCap;

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId( int typeId ) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getRequiredLevel() {
		return requiredLevel;
	}

	public void setRequiredLevel(short requiredLevel) {
		this.requiredLevel = requiredLevel;
	}

	public short getStackCap() {
		return stackCap;
	}

	public void setStackCap(short stackCap) {
		this.stackCap = stackCap;
	}
	
	
	public abstract void parseXml();
	

}
