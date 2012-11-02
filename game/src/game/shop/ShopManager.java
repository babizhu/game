package game.shop;

import game.AwardType;
import game.prop.BaseProp;
import game.prop.cfg.PropTempletCfg;
import game.prop.templet.BasePropTemplet;
import user.UserInfo;
import util.ErrorCode;

/**
 * 商城系统
 * @author liukun
 * 2012-10-23 下午02:38:53
 */
public class ShopManager {
	
	/**
	 * 玩家从商城购买道具
	 * @param user					玩家
	 * @param templetId				道具模板ID
	 * @param count					数量
	 * @param buyType				购买类型
	 * @return
	 */
	ErrorCode buy( UserInfo user, short templetId, short count, AwardType buyType ){
		BasePropTemplet t = PropTempletCfg.getTempletById( templetId );
		if( t == null ){
			return ErrorCode.PROP_NOT_ENOUGH;
		}
		if( !t.isOpen() ){
			return ErrorCode.SHOP_CANT_BUY;
		}
		int price = 0;
		price = buyType == AwardType.GOLD ? t.getPriceOfGold() : t.getPriceOfCash();
		price *= count;
		price *= t.getDiscount();//考虑折扣
		
		synchronized (user) {
			//if( user.getProp)//检测背包格子是否足够
			if( buyType == AwardType.GOLD ){
				if( user.changeGold( -price, "ShopManager.buy" ) < 0 ){
					return ErrorCode.USER_GOLD_NOT_ENOUTH;
				}
			}
			else{
				if( user.changeCash( -price, "ShopManager.buy" ) < 0 ){
					return ErrorCode.USER_CASH_NOT_ENOUTH;
				}
			}
			//user.getProp
		}
		
		//刷新客户端
		return ErrorCode.SUCCESS;
		
	}
	
	ErrorCode sell( UserInfo user, short templetId, short count ){
		return null;
		
	}
	public static void main(String[] args) {
		int i = 90;
		float f = 0.3f;
		i *= f;
		System.out.println( i );
	}

}
