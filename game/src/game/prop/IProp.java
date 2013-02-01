package game.prop;

import game.prop.templet.BasePropTemplet;

public interface IProp {
	
	/**
	 * 获取该道具相对应的模板
	 * @return
	 */
	BasePropTemplet	getTemplet( );
	
	
//	ErrorCode run( UserInfo user );

}
