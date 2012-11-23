package game;

import game.fighter.BaseFighter;

import java.util.List;

public interface IFighterTeam {

	/**
	 * 是否所有的战士都已经死亡
	 * @return
	 */
	boolean allDie();

	/**
	 * 获取所有的参战人员
	 * @return
	 */
	List<BaseFighter> getFighters();
}
