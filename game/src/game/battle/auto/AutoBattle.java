package game.battle.auto;

import game.battle.BaseBattle;
import game.battle.BaseBattleStatus;
import game.battle.auto.buff.BuffManager;
import game.fighter.BaseFighter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import util.ErrorCode;



/**
 * 自动回合制
 * @author liukun
 * 2012-9-27 下午05:52:32
 */
public class AutoBattle extends BaseBattle {

	/**
	 * 攻方列表
	 */
	private final List<RunTimeFighter> attackers  = new ArrayList<RunTimeFighter>();
	
	/**
	 * 守方列表
	 */
	private final List<RunTimeFighter> defenders = new ArrayList<RunTimeFighter>();
	
	/**
	 * 所有参战战士的列表
	 */
	private final List<RunTimeFighter> fighters = new ArrayList<RunTimeFighter>();
	
	int currentRound = MAX_ROUND;
	
	private static final Comparator<RunTimeFighter> speedComparator = new Comparator<RunTimeFighter>(){
		@Override
		public int compare( RunTimeFighter f1, RunTimeFighter f2 ) {
			return f1.fighter.getSpeed() - f1.fighter.getSpeed();
		}
	};

	private static final int MAX_ROUND = 50;//最大回合数
	
	public AutoBattle( List<BaseFighter> attackers, List<BaseFighter> defenders ) {
		super();
		if( attackers == null || attackers.size() == 0 ){
			throw new IllegalArgumentException( "攻方战士列表为空或者0" );
		}
		if( defenders == null || defenders.size() == 0 ){
			throw new IllegalArgumentException( "守方战士列表为空或者0" );
		}
		for( BaseFighter fighter : attackers ){
			RunTimeFighter rf = new RunTimeFighter( fighter );
			rf.isLeft = true;
			this.attackers.add( rf );
		}
		for( BaseFighter fighter : defenders ){
			RunTimeFighter rf = new RunTimeFighter( fighter );
			rf.isLeft = false;
			this.defenders.add( rf );
		}
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init(){
		fighters.addAll( attackers );
		fighters.addAll( defenders );
		
		Collections.sort( fighters, speedComparator );
	}

	@Override
	public ErrorCode run() {
		boolean isEnd = false;
		while( !isEnd ){
			for( RunTimeFighter rf : fighters ){
				BaseFighter attacker = rf.fighter;//本次的攻击者
				if( attacker.getHp() <= 0 ){
					continue;
				}
				List<RunTimeFighter> currentDefenders;
				if( rf.isLeft ){
					currentDefenders = defenders;
				}
				else{
					currentDefenders = attackers;
				}				
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseBattleStatus getParam() {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args) {
		BaseFighter f = new BaseFighter();
		f.setHp( 100 );
		List<BaseFighter> list1 = new ArrayList<BaseFighter>();
		list1.add( f );
		
		List<BaseFighter> list2 = new ArrayList<BaseFighter>();
		list2.addAll( list1 );
		
		list1.get(0).setHp( 20 );
		
		System.out.println( list2.get(0).getHp() );
		
		System.out.println( list1.get(0) );
		System.out.println( list2.get(0) );
		
		
	}

}

/**
 * 只有在战斗当才有必要必须依附到战士身上的一些信息
 */
class RunTimeFighter{
	
	public RunTimeFighter( BaseFighter fighter ) {
		this.fighter = fighter;
	}
	
	BaseFighter			fighter;
	boolean 			isCanHit = false;
	/*
	 * 是否处于战场的左边
	 */
	boolean				isLeft;
	
	BuffManager			bm	= new BuffManager(fighter);
	
	
}