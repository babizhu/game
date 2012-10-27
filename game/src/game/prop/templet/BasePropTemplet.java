package game.prop.templet;


import game.prop.BaseProp;

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
	private short		stackCapacity;
	
	/**
	 * 系统回购价格
	 * @return
	 */
	private int			buyBack;
	
	/**
	 * 系统卖出价，单位点券
	 */
	private int			priceOfGold;
	
	/**
	 * 系统卖出价，单位现金
	 */
	private int			priceOfCash;

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

	public short getStackCapacity() {
		return stackCapacity;
	}

	public void setStackCapacity(short stackCapacity) {
		this.stackCapacity = stackCapacity;
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
		setStackCapacity( Short.parseShort( element.getChildText( "stackCap" ) ) );
		setRequiredLevel( Short.parseShort( element.getChildText( "requiredLevel" ) ) );
		setBuyBack( Integer.parseInt( element.getChildText( "buyBack" ) ) );
	}

	public int getPriceOfGold() {
		return priceOfGold;
	}

	public void setPriceOfGold(int priceOfGold) {
		this.priceOfGold = priceOfGold;
	}

	public int getPriceOfCash() {
		return priceOfCash;
	}

	public void setPriceOfCash(int priceOfCash) {
		this.priceOfCash = priceOfCash;
	}

	@Override
	public String toString() {
		return "BasePropTemplet [templetId=" + templetId + ", name=" + name
				+ ", requiredLevel=" + requiredLevel + ", stackCapacity="
				+ stackCapacity + ", buyBack=" + buyBack + ", priceOfGold="
				+ priceOfGold + ", priceOfCash=" + priceOfCash + "]";
	}

	public abstract BaseProp createProp();

	


}
