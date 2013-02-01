package game.prop;

import game.prop.templet.BasePropTemplet;

/**
 * 用于嵌套在装备上的宝石
 * @author Administrator
 * 2013-1-31 下午4:49:11
 */
public class Gem implements IProp{

	private	long		id;
	
	@Override
	public BasePropTemplet getTemplet() {
		return null;
	}

	public long getId() {
		return id;
	}
}
