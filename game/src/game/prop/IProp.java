package game.prop;

import game.prop.templet.PropTempletBase;

public interface IProp {
	
	/**
	 * 获取该道具相对应的模板
	 * @return
	 */
	PropTempletBase	getTemplet( );
}
