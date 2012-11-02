package game.prop.templet;

import org.jdom2.Element;

/***
 * 装备道具，例如黄金头盔，五彩头盔，神级七彩护甲
 * @author liukun
 * 2012-10-12 下午12:10:54
 */
public class EquipmentTemplet extends BasePropTemplet{

	/**
	 * 每升一级的强化数值
	 */
	private int			upNum;
	
	public int getUpNum() {
		return upNum;
	}

	public void setUpNum(int upNum) {
		this.upNum = upNum;
	}

	/* (non-Javadoc)
	 * @see game.prop.cfg.BasePropTemplet#parse(org.jdom2.Element)
	 */
	@Override
	public void parse(Element element) {
		super.parse( element );
		
		setUpNum( Integer.parseInt( element.getChildText( "upNum" ) ) );
	}

	@Override
	public String toString() {
		String s = super.toString();
		s = s.substring( 0, s.length() - 1 );//去掉"]"
		return s + ", upNum=" + upNum + "]";
	}

	@Override
	public boolean isEquipment() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
