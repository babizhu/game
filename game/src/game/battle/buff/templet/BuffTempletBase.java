package game.battle.buff.templet;


import game.battle.buff.BuffRunPoint;

public class BuffTempletBase {
	/**
	 * buff的ID
	 */
	public final byte								id;

	/**
	 * 持续回合数
	 */
	private int										duratioRound = 2;
	/**
	 * 是否为减低buff，例如中毒效果
	 */
	private boolean									isDeBuff = false;

	private	String									name;
	private String									desc;
	
	
	public BuffTempletBase( byte id ){
		this.id = id;
	}

	public BuffRunPoint getRunPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getDuratioRound() {
		return duratioRound;
	}

	public void setDuratioRound(int duratioRound) {
		this.duratioRound = duratioRound;
	}

	public boolean isDeBuff() {
		return isDeBuff;
	}

	public void setDeBuff(boolean isDeBuff) {
		this.isDeBuff = isDeBuff;
	}

	public void setName(String childText) {
		// TODO Auto-generated method stub
		
	}	
}
