package game.battle;

import game.fighter.BaseFighter;

public interface IBattleUtil {

	/**
	 * 判断攻击是否命中
	 * @param attacker
	 * @param defender
	 * @return
	 */
	boolean isHit(BaseFighter attacker, BaseFighter defender);

}
