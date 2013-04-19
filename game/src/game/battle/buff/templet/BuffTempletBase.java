package game.battle.buff.templet;

import game.battle.buff.BuffRunPoint;

public class BuffTempletBase {

	/**
	 * 持续回合数
	 */
	private int										round = 2;
	/**
	 * 是否为减低buff，例如中毒效果
	 */
	private boolean									isDeBuff = false;
	
	public boolean isDebuff() {
		return isDeBuff;
	}

	public BuffRunPoint getRunPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRound() {
		return round;
	}
	
}
