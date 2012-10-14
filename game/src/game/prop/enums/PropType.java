/**
 * 
 */
package game.prop.enums;

import game.prop.cfg.*;

/**
 * @author liukun
 * 2012-10-14
 */
public enum PropType {
	/**
	 *	材料类道具 
	 */
	STUFF {
		@Override
		public BasePropTemplet createNewTemplet() {
			return new StuffTemplet();
		}
	},
	
	/**
	
	 * 装备类道具
	 */
	EQUIPMENT {
		@Override
		public EquipmentTemplet createNewTemplet() {
			return new EquipmentTemplet();
		}
	},;

	/**
	 * @return
	 */
	public abstract BasePropTemplet createNewTemplet();
	

}
