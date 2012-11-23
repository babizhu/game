package game.battle.auto;

import game.battle.IBattleUtil;
import game.fighter.BaseFighter;

public class AutoBattleUtil implements IBattleUtil {

	public static final IBattleUtil INSTANCE = new AutoBattleUtil();
	private AutoBattleUtil() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isHit(BaseFighter attacker, BaseFighter defender) {
		// TODO Auto-generated method stub
		return false;
	}

}
