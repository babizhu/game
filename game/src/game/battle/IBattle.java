package game.battle;

import java.nio.ByteBuffer;

import util.ErrorCode;

public interface IBattle {

	/**
	 * 运行战斗
	 * @return
	 */
	ErrorCode run();
	
	/**
	 * 返回战斗结束后需要获取的战斗信息
	 * @return
	 */
	ByteBuffer getWarSituation();
	
	/**
	 * 攻击方是否获胜
	 * @return
	 */
	boolean getAttackerIsWin();
}
