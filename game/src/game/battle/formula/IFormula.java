package game.battle.formula;

import game.fighter.BaseFighter;

public interface IFormula {

	int run( BaseFighter attacker, BaseFighter defender, Object arguments );

}
