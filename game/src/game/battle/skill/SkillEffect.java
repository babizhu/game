package game.battle.skill;

import java.util.Arrays;

import game.battle.formula.Formula;
import game.fighter.FighterAttribute;

public class SkillEffect {
	
	private FighterAttribute			attribute;
	private Formula						formula;
	private float[]						arguments;
	
	
	public FighterAttribute getAttribute() {
		return attribute;
	}
	public void setAttribute(FighterAttribute attribute) {
		this.attribute = attribute;
	}
	public Formula getFormula() {
		return formula;
	}
	public void setFormula( Formula formula) {
		this.formula = formula;
	}
	public float[] getArguments() {
		return arguments;
	}
	public void setArgument(float[] arguments) {
		this.arguments = arguments;
	}
	@Override
	public String toString() {
		return "SkillEffect [attribute=" + attribute + ", formula=" + formula
				+ ", arguments=" + Arrays.toString(arguments) + "]";
	}
	
	
}
