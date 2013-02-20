package game.prop;

import game.prop.templet.PropTempletBase;

public interface IProp {
	
	/**
	 * 获取该道具相对应的模板
	 * @return
	 */
	PropTempletBase	getTemplet( );
	
	/**
	 * 构建用于网络传输的byteBuffer
	 */
	void buildContent();
//	ErrorCode run( UserInfo user );

}
