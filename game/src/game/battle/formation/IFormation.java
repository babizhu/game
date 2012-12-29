package game.battle.formation;

import game.battle.Pet;
import game.fighter.BaseFighter;

import java.util.List;

public interface IFormation {

	Pet				  		getPet();
	/**
	 * 获取所有的战士
	 * @return
	 */
	List<BaseFighter> 		getAllFighters();
	
	/**
	 * 获取普通攻击时应该被攻击的玩家
	 * @param attacker	攻方
	 * @return
	 */
	BaseFighter getDefender( BaseFighter attacker );
	
	/**
	 * 根据类型来得到相应的受到效果的战士列表
	 * @param attacker	攻方
	 * @return
	 */
	List<BaseFighter> getDefender( BaseFighter attacker, GetDefenderType type );
	
	/**
	 * 是否所有的战士都已经死亡
	 * @return
	 */
	boolean isAllDie();
	BaseFighter getMinHp();	
	
//	int		getRowByFighter( BaseFighter fighter );
//	int		getColByFighter( BaseFighter fighter );
	
}
