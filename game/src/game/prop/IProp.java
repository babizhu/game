package game.prop;

import game.prop.templet.BasePropTemplet;
import util.ErrorCode;

public interface IProp {
	
	/**
	 * 获取该道具相对应的模板
	 * @return
	 */
	BasePropTemplet	getTemplet( );
	
	ErrorCode put( BaseProp prop );
	
//	ErrorCode run( UserInfo user );

}
