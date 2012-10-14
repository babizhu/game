package game.prop.cfg;


import org.jdom2.Element;


public abstract class BasePropTemplet {
	
	/**
	 * 道具模版id
	 */
	private short 		templetId;
	
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
	
	/**
	 * 系统回购价格
	 * @return
	 */
	private int			buyBack;

	public short getTempletId() {
		return templetId;
	}

	public void setTempletId( short templetId ) {
		this.templetId = templetId;
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
	
	
	public int getBuyBack() {
		return buyBack;
	}

	public void setBuyBack(int buyBack) {
		this.buyBack = buyBack;
	}

	/**
	 * 根据不同的道具类型调用相应的道具解析函数
	 * @param element
	 * @return
	 */
	public void parse( Element element ){
		setTempletId( Short.parseShort( element.getChildText( "templetId" ) ) );
		setName( element.getChildText( "name" ) );
		setStackCap( Short.parseShort( element.getChildText( "stackCap" ) ) );
		setRequiredLevel( Short.parseShort( element.getChildText( "requiredLevel" ) ) );
		setBuyBack( Integer.parseInt( element.getChildText( "buyBack" ) ) );
	}

	@Override
	public String toString() {
		return "BasePropTemplet [templetId=" + templetId + ", name=" + name
				+ ", requiredLevel=" + requiredLevel + ", stackCap=" + stackCap
				+ ", buyBack=" + buyBack + "]";
	}



}
