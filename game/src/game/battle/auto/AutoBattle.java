package game.battle.auto;

import game.battle.AttackType;
import game.battle.BaseBattle;
import game.battle.BuffRunPoint;
import game.battle.FighterTeam;
import game.battle.IBattleUtil;
import game.battle.IFormation;
import game.battle.Pet;
import game.fighter.BaseFighter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 自动回合制的战斗模式
 * @author liukun
 * 2012-9-27 下午05:52:32
 */
public class AutoBattle extends BaseBattle {

	private static final  Logger 		logger = LoggerFactory.getLogger( AutoBattle.class );
	private static final IBattleUtil	until = AutoBattleUtil.INSTANCE;

	private static final int 			SP_TO_ADD = 50;
	private static final float 			BLOCK_DAMAGE_RATE = 0.5f;
	
	
	private static final int 			MAX_ROUND = 50;//最大回合数

	/**
	 * 攻方列表
	 */
	private final FighterTeam			attackers;
	
	/**
	 * 守方列表
	 */
	private final FighterTeam 			defenders;
	
	/**
	 * 所有参战战士的列表
	 */
	private final List<BaseFighter>		fighters = new ArrayList<BaseFighter>();
	
	private int 						currentRound = 0;
	
	/**
	 * 胜负结果
	 */
	private boolean 					attackerIsWin;
	
	/**
	 * 战况信息
	 */
	private ByteBuffer					warSituation;
	
	private static final int 			SKILL_ATTACK_NEED_SP = 0;
	
	/**
	 * 九宫格阵型
	 */
	private IFormation					formation = FormationNine.INSTANCE;	
	
	/**
	 * 对所有参战人员按速度进行排序
	 */
	private static final Comparator<BaseFighter> speedComparator = new Comparator<BaseFighter>(){
		@Override
		public int compare( BaseFighter f1, BaseFighter f2 ) {
			return f2.getSpeed() - f1.getSpeed();
		}
	};


	
	public AutoBattle( FighterTeam attackers, FighterTeam defenders ) {
		super();
		
		this.attackers = attackers;
		this.defenders = defenders;
		
		warSituation = ByteBuffer.allocate( 2048 );//2048是否足够？
		
		attackerIsWin = true;
		init();
	}
	
	/**
	 * 初始化，按照速度对参战人员进行排序
	 */
	private void init(){		
		for( BaseFighter f : attackers.getFighters() ){
			
			fighters.add( f );
		}
		for( BaseFighter f : defenders.getFighters() ){
			fighters.add( f );
		}
		Collections.sort( fighters, speedComparator );
	}

	@Override
	public void run() {
		boolean isEnd = false;
		while( !isEnd ){
			pet();
			for( BaseFighter currentAttacker : fighters ){
				if( currentAttacker.getHp() <= 0 ){
					continue;
				}

				warSituation.put( AttackType.BEGIN_ROUND.toNumber() );					    //标识一个回合开始

				int damage = currentAttacker.getBm().run( 0, BuffRunPoint.BEFORE_ATTACK );
				if( damage != 0 && reduceHp( currentAttacker, damage ) ){
					isEnd = true;
					break;
				}
				if( currentAttacker.getHp() <= 0 || !currentAttacker.isCanHit() ){
					continue;
				}
				
				FighterTeam currentDefenderTeam = currentAttacker.isLeft() ?  defenders : attackers;
				
				if( currentAttacker.getSp() >= SKILL_ATTACK_NEED_SP ){
					if( doSkillAttack( currentAttacker, currentDefenderTeam  ) ){
						isEnd = true;
						break;
					}
				}
				else{
					if( doNormalAttack( currentAttacker, currentDefenderTeam ) ){
						isEnd = true;
						break;
					}		
				}				
			}
			currentRound++;
			if( currentRound == MAX_ROUND ){
				logger.info( "当前回合数" + currentRound + "超过" + MAX_ROUND + "了！！！！！\n" );
				this.attackerIsWin = false;//平局的情况算防守方胜利
				isEnd = true;
			}
		}
	}

	private boolean doSkillAttack(BaseFighter currentAttacker, FighterTeam currentDefenderTeam) {
		return false;
	}

//	/**
//	 * 传入进攻团队，返回防守团队
//	 * 传入防守团队，返回进攻团队
//	 * @param team
//	 * @return
//	 */
//	private FighterTeam reverseTeam( FighterTeam team ){
//		return (team == attackers) ? defenders : attackers;
//	}
	
	/**
	 * 普通攻击
	 * @param attacker		当前攻击者
	 * @param defenderTeam	当前的防守队伍
	 * @return
	 */
	private boolean doNormalAttack( BaseFighter attacker, FighterTeam defenderTeam ) {
		warSituation.put( (byte) AttackType.NORMAL_ATTACK.toNumber() );
		BaseFighter defender = formation.normalAttackWho( attacker, defenderTeam );
		
		assert( defender != null );
		
		boolean hit = until.isHit( attacker, defender );
		warSituation.put( (byte) (hit ? 1 : 0) );
		warSituation.put( attacker.getPosition() );//攻击者所在位置
		warSituation.put( defender.getPosition() );//防御者所在位置	，为了方便客户端处理，把攻防两边的战士的位置，已串行化处理，攻方范围0-8，防御方9-17

		if( !hit ){
			return false;
		}
		
		int damage = until.calcNormalAttackDamage( attacker, defender );
		byte crit = until.calcCrit( attacker, defender );//计算暴击加成
		damage *= crit;		
		//执行运行时间点为【被普通或者技能攻击后，正式扣血前】DEFENDING的buff
		damage = defender.getBm().run( (int) damage, BuffRunPoint.AFTER_DEFENDING );
		
		//boolean isBlockAndCounterAttack = until.isBlockAndCounterAttack( defender, attacker );	//暂时不考虑格挡这一说
		warSituation.putInt( damage );
		
		if( reduceHp( defender, damage ) == true ){
			return true;
		}
		

		attacker.setSp( attacker.getSp() + SP_TO_ADD );
		if( damage > 1 ){//防止不死之身之类的技能长久不结束
			defender.setSp( defender.getSp() + SP_TO_ADD );
		}
		
		return doCounterAttack( defender, attacker );
	}

	/**
	 * 反击流程，不用考虑命中与暴击，同样也没考虑防御者身上的buff，暂时先这样，有需求在修改
	 * @param attacker
	 * @param defender
	 * @param defenderTeam
	 * @return
	 */
	private boolean doCounterAttack( BaseFighter attacker, BaseFighter defender ) {
		if( attacker.getHp() == 0 ){
			return false;
		}
		boolean isCounterAttack = until.isBlockAndCounterAttack( defender, attacker );	//判断是否反击
		if( !isCounterAttack ){
			return false;
		}

		int damage = until.calcNormalAttackDamage( attacker, defender );
		damage *= BLOCK_DAMAGE_RATE;

		warSituation.put( (byte) AttackType.COUNTER_ATTACK.toNumber() );//反击标识		
		warSituation.put( attacker.getPosition() );//攻击者所在位置
		warSituation.put( defender.getPosition() );//防御者所在位置
		warSituation.putInt( (int) damage );//伤害值

		return reduceHp( defender, damage );
	}
	
	private void pet() {
		Pet pet = attackers.getPet();
		if( pet != null ){
			pet.run( this );
		}
		// TODO Auto-generated method stub
		
	}

	/**
	 * 获取本方所有的战士列表
	 * @param rf
	 * @return
	 */
	private FighterTeam getTeam( BaseFighter fighter ){
		return fighter.isLeft() ? attackers : defenders;			
	}
	/**
	 * 对被攻击方进行扣hp等一系列操作<br>
	 * 如果一方全军覆没，则返回true
	 * 
	 * @param defender			被攻击的战士
	 * @param damage
	 * @return
	 * 			true			被攻击方全军覆没了<br>
	 * 			false			被攻击方继续存活
	 */
	public boolean reduceHp( BaseFighter defender, int damage ){
		defender.setHp( defender.getHp() - damage );
		
		if( defender.getHp() <= 0 ){//被攻击战士已经挂了
			
			if( getTeam( defender ).allDie() ){
				return true;
			}
		}
		return false;
	}

//	@Override
//	public BaseBattleStatus getBattleStatus() {
//		return status;
//	}

	@Override
	public ByteBuffer getWarSituation() {
		return warSituation;
	}
	
	@Override
	public boolean getAttackerIsWin() {
		return attackerIsWin;
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
		
		int c = 20;
		int c1 = ++c;
		System.out.println(c1);
		
		float r  = 0.5f;
		int s= 100;
		s *= r;
		System.out.println( s );
//		ByteBuffer b = ByteBuffer.allocate(10);
//		b.put( 30);
	}


	

}