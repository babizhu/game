package game.battle.buff;

import game.battle.buff.templet.BuffTempletBase;
import game.fighter.FighterBase;


/**
 * 最为简单的buf形式
 * @author Administrator
 * 2013-3-1 上午11:21:30
 */
public class NormalBuff extends BuffBase {

	private final BuffTempletBase 	templet;
	private int						round;
	
	public NormalBuff(FighterBase receiver, FighterBase sender, byte templetId ) {
		super( receiver, sender );
		templet = TempletCfg.get( templetId );
		round = templet.getRound();
	}

	@Override
	public int run(int damage) {
		if( round-- != 0 && !isRemove){
			
		}
		else{
			isRemove = true;
		}
		return damage;
	}

	@Override
	public BuffRunPoint getBuffRunPoint() {
		return templet.getRunPoint();
	}
	public static void main(String[] args) {
		System.out.println( Byte.MAX_VALUE);
	}
}
