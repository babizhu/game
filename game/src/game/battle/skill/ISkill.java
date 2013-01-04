package game.battle.skill;

import game.battle.formation.IFormation;
import game.fighter.BaseFighter;

import java.util.List;

/**
 * 战斗技能接口
 * @author liukun
 * 2012-9-27 下午05:44:22
 */
public interface ISkill {
	

	List<SkillResult> run(BaseFighter attacker, IFormation friend, IFormation enemy); 

}
