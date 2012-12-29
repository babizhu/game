package game.battle.auto.skill;

import game.battle.ISkill;
import game.battle.SkillResult;
import game.battle.formation.IFormation;
import game.fighter.BaseFighter;

import java.util.ArrayList;
import java.util.List;

/**
 * 恢复自身气血上限的15%给当前气血最低的友方单位。

 * @author liukun
 * 2012-12-29 下午01:56:13
 */
public class S001 implements ISkill {

	@Override
	public List<SkillResult> run( BaseFighter attacker, IFormation friend, IFormation enemy ) {
		List<SkillResult> result = new ArrayList<SkillResult>();
		
		BaseFighter obj = friend.getMinHp();//想办法处理
		SkillResult r1 = new SkillResult( obj );
		r1.info
		
		
		return result;
	}

}
