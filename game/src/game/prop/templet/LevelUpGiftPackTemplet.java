package game.prop.templet;

import org.jdom2.Element;

import game.award.Award;
import game.award.AwardType;

/**
 * 为玩家升级所准备的大礼包
 * @author liukun
 * 2013-1-29 上午10:45:30
 */
public class LevelUpGiftPackTemplet extends BasePropTemplet {

	private Award	award;
	@Override
	public boolean isEquipment() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void parse(Element element) {
		super.parse( element );
		
		award = new Award( AwardType.LEVEL_UP_GIFT_PACK, element.getChildText( "content" ) );
	}

	@Override
	public String toString() {
		String s = super.toString();
		s = s.substring( 0, s.length() - 1 );//去掉"]"
		return s + ", award=" + award + "]";
	}

}
