package game.battle.skill;

import java.util.ArrayList;
import java.util.List;

import game.fighter.BaseFighter;
import game.fighter.FighterAttribute;

public class SkillResult {
	BaseFighter			fighter;
	List<info>			info = new ArrayList<info>();
	public SkillResult(BaseFighter fighter) {
		super();
		this.fighter = fighter;
	}
	
	
	
}
class info{
	FighterAttribute 	attribute;
	int					value;
}
