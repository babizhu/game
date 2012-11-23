package game.battle;

import game.fighter.BaseFighter;

import java.util.List;


public interface IFormation {

	/**
	 * 计算普通攻击应该攻击的战士
	 * @param attacker					攻方战士
	 * @param defenders					防御方团队
	 * 					
	 * @return							被攻击的战士
	 * 			
	 */
	BaseFighter normalAttackWho( BaseFighter attacker, FighterTeam defender);
	
	/**
	 * 获取攻击战士所在行的敌方所有有战士
	 * @param row
	 * @param defender
	 * @return
	 */
	List<BaseFighter> getFightersByRow( BaseFighter attacker, FighterTeam defender );
	
	/**
	 * 获取该列的所有战士
	 * @param col
	 * @param fighters
	 * @return
	 */
	public List<BaseFighter> getFightersByCol( BaseFighter attacker, FighterTeam defender );
	
	/**
	 * 获取九宫格处于十字星位置的战士
	 * @param fighters
	 * @return
	 */
	public List<BaseFighter> getFightersByCross( FighterTeam defender );

	/**
	 * 获取九宫格处于X字星位置的战士
	 * @param fighters
	 * @return
	 */
	public List<BaseFighter> getFightersByX ( FighterTeam defender );

	/**
	 * 通过玩家等级获取可以出战的精英员工数量<br />
	 * 30级以下 		2个 <br />
	 * 30级~44级 	3个<br />
	 * 45级~55级 	4个<br />
	 * 55级以上 		5个<br /> 
	 * @param level
	 * @return
	 */
	int getMaxFighterNmuberByLevel(int level);

	

}
