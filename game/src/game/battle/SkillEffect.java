package game.battle;

import game.battle.formula.IFormula;
import game.fighter.FighterAttribute;

public class SkillEffect {
	
	private FighterAttribute			attribute;
	private IFormula					formula;
	private float						argument1;
	
	
	public FighterAttribute getAttribute() {
		return attribute;
	}
	public void setAttribute(FighterAttribute attribute) {
		this.attribute = attribute;
	}
	public IFormula getFormula() {
		return formula;
	}
	public void setFormula(IFormula formula) {
		this.formula = formula;
	}
	public float getArgument1() {
		return argument1;
	}
	public void setArgument1(float argument1) {
		this.argument1 = argument1;
	}
	
	

}
