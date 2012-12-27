package game.battle;

import game.battle.auto1.WarSituation;


public interface IBattle {

	/**
	 * 运行战斗
	 * @return
	 */
	void run();
	
	/**
	 * 返回战斗结束后需要获取的战斗信息
	 * @return
	 */
	WarSituation getWarSituation();
	
	/**
	 * 攻击方是否获胜
	 * @return
	 */
	boolean getAttackerIsWin();
}
