package game.battle;

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
	BaseBattleStatus getParam();
}
