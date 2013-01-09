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
	List<BaseFighter> getFighterOnEffect( BaseFighter attacker, ChooseFighters type );
	
	/**
	 * 是否所有的战士都已经死亡
	 * @return
	 */
	boolean isAllDie();
	
//	/**
//	 * 对防守方进行一系列改造，包括
//	 * 所有位置+9
//	 * 镜面翻转
//	 * 重新按照位置信息排序
//	 * 对于mission里面的阵型而言，可以在读取配置表的时候，就执行此代码，避免每次战斗前反复执行
//	 */
//	void formatDefender( BaseFighter fighter );	
	
	
	
}
