package game.battle.skill;

import game.battle.formula.IFormula;
import game.fighter.FighterAttribute;

public class SkillEffect {
	
	private FighterAttribute			attribute;
	private IFormula					formula;
	private float[]						arguments;
	
	
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
	public float[] getArguments() {
		return arguments;
	}
	public void setArgument(float[] arguments) {
		this.arguments = arguments;
	}
	
	

}
