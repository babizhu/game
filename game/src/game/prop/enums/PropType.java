/**
 * 
 */
package game.prop.enums;

import game.prop.templet.*;

/**
 * @author liukun
 * 2012-10-14
 */
public enum PropType {
	/**
	 *	材料类道具 ，不具备唯一id
	 */
	STUFF {
		@Override
		public PropTempletBase create() {
			return new StuffTemplet();
		}
	},
	
	/**
	
	 * 装备类道具，具备唯一id，可升级的，例如武器，宝石，勋章
	 */
	EQUIPMENT {
		@Override
		public EquipmentTemplet create() {
			return new EquipmentTemplet();
		}
	},
	/**
	 * 升级用的大礼包道具，不具备唯一id
	 */
	LEVEL_UP_GIFT_PACK{
		@Override
		public LevelUpGiftPackTemplet create() {
			return new LevelUpGiftPackTemplet();
		}
	};
	

	/**
	 * @return
	 */
	public abstract PropTempletBase create();
}
