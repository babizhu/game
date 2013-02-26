package game.battle.formation;

import game.battle.Pet;
import game.fighter.FighterBase;

import java.util.List;

public interface IFormation {

	Pet				  		getPet();
	/**
	 * 获取所有的战士
	 * @return
	 */
	List<FighterBase> 		getAllFighters();
	
	/**
	 * 获取普通攻击时应该被攻击的玩家
	 * @param attacker	攻方
	 * @return
	 */
	FighterBase getBaseDefender( FighterBase attacker );
	
	/**
	 * 根据类型来得到相应的受到效果的战士列表
	 * @param attacker	攻方
	 * @return
	 */
	List<FighterBase> getFighterOnEffect( FighterBase attacker, ChooseFighters type );
	
	/**
	 * 是否所有的战士都已经死亡
	 * @return
	 */
	boolean isAllDie();
	
}
