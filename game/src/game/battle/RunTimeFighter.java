package game.battle;

import game.battle.auto.buff.BuffManager;
import game.fighter.BaseFighter;

/**
 * 只有在战斗当才有必要必须依附到战士身上的一些信息
 */
public class RunTimeFighter {
	
	private BaseFighter			fighter;
	private boolean 			isCanHit = false;
	
	/*
	 * 是否处于战场的左边
	 */
	private boolean				isLeft;
	
	private BuffManager			bm;

	public RunTimeFighter( BaseFighter fighter ) {
		this.fighter = fighter;
		this.bm = new BuffManager();
	}	

	public BaseFighter getFighter() {
		return fighter;
	}

	public void setFighter(BaseFighter fighter) {
		this.fighter = fighter;
	}

	public boolean isCanHit() {
		return isCanHit;
	}

	public void setCanHit(boolean isCanHit) {
		this.isCanHit = isCanHit;
	}

	public boolean isLeft() {
		return isLeft;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	public BuffManager getBm() {
		return bm;
	}	
}
