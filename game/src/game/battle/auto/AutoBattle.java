package game.battle.auto;

import game.battle.BaseBattle;
import game.battle.BuffRunPoint;
import game.battle.IBattleUtil;
import game.battle.Pet;
import game.battle.formation.ChooseFighters;
import game.battle.formation.IFormation;
import game.battle.skill.SkillEffect;
import game.battle.skill.SkillTemplet;
import game.fighter.BaseFighter;
import game.fighter.FighterAttribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 自动回合制的战斗模式
 * @author liukun
 * 2012-9-27 下午05:52:32
 */
public class AutoBattle extends BaseBattle {

	private static final Logger 		logger = LoggerFactory.getLogger( AutoBattle.class );
	private static final IBattleUtil	util = AutoBattleUtil.getInstance();

	private static final int 			SKILL_ATTACK_NEED_SP = 0;
	private static final int 			SP_TO_ADD = 50;		
	private static final int 			MAX_ROUND = 50;//最大回合数

	/**
	 * 攻方列表
	 */
	private final IFormation			attackers;
	
	/**
	 * 守方列表
	 */
	private final IFormation 			defenders;
	
	/**
	 * 所有参战战士的列表
	 */
	private final List<BaseFighter>		allFighters = new ArrayList<BaseFighter>();
	
	private int 						currentRound = 0;
	
	/**
	 * 胜负结果
	 */
	private boolean 					attackerIsWin;
	
	/**
	 * 战况信息
	 */
	private WarSituation				warSituation;	

	public AutoBattle( IFormation attackers, IFormation defenders ) {
		super();		
		this.attackers = attackers;
		this.defenders = defenders;
		
		attackerIsWin = true;
		init();
	}
	/**
	 * 初始化，按照速度对参战人员进行排序
	 */
	private void init(){		
		for( BaseFighter f : attackers.getAllFighters() ){			
			allFighters.add( f );
		}
		for( BaseFighter f : defenders.getAllFighters() ){
			f.setPosition( (byte) (f.getPosition() + Formation9.TOTAL_COUNT) );
			allFighters.add( f );
		}
		Collections.sort( allFighters, util.getOrderComparator() );
	}

	private IFormation getFormation( BaseFighter fighter ){
		return fighter.isLeft() ?  attackers : defenders;
	}
	
	@Override
	public void run() {
		boolean isEnd = false;
		while( !isEnd ){
			pet();
			warSituation.putRoundFlag();
			for( BaseFighter currentAttacker : allFighters ){
				if( currentAttacker.isDie() ){
					continue;
				}

				int damage = currentAttacker.getBm().run( 0, BuffRunPoint.BEFORE_ATTACK );
				if( damage != 0 && reduceHp( currentAttacker, damage ) ){
					isEnd = true;
					break;
				}
				if( currentAttacker.isDie() || !currentAttacker.isCanHit() ){
					continue;
				}
				
				IFormation currentDefender = currentAttacker.isLeft() ?  defenders : attackers;
				
				if( currentAttacker.getSp() >= SKILL_ATTACK_NEED_SP ){
					if( doSkillAttack( currentAttacker, currentDefender  ) ){
						isEnd = true;
						break;
					}
				}
				else{
					if( doAttack( currentAttacker, currentDefender ) ){
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

	private boolean doSkillAttack(BaseFighter attacker, IFormation currentDefenderTeam) {
		SkillTemplet templet = attacker.getSkillTemplet();
		ChooseFighters type = templet.getEnemys();
		if( type != null ){
			for( BaseFighter f : currentDefenderTeam.getFighterOnEffect( attacker, type ) ){
				
				if( doSkillEffect( f, templet.getEffectOnEnemy() ) )//此战士挂了
				{
					if( currentDefenderTeam.isAllDie() ){
						return true;
					}
					
				}
			}
		}
		type = templet.getFriends();
		if( type != null ){
			List<BaseFighter> friends = currentDefenderTeam.getFighterOnEffect(attacker, type );
		}
		return false;
		
	}
	/**
	 * 
	 * @param fighter		受到技能影响的战士
	 * @param effects		技能影响的内容
	 * @return
	 * 				true	此战士挂了
	 * 				false	此战士未死
	 */
	private boolean doSkillEffect( BaseFighter fighter, List<SkillEffect> effects ){
		
		for( SkillEffect se : effects ){
//			int numberToChange = se.getFormula().run( se.getArgument1() );
//			se.getAttribute().run( fighter, numberToChange );
//			
//			//考虑warSituation
//			if( se.getAttribute() == FighterAttribute.HP && fighter.getHp() <= 0 ){
//				return true;
//			}
			switch( se.getAttribute() ){
			case HP:
				
				break;
			case SP:
				break;
			}
		}
		return false;
	}
	
	/**
	 * 普通攻击
	 * @param attacker		当前攻击者
	 * @param defenderTeam	当前的防守队伍
	 * @return
	 */
	private boolean doAttack( BaseFighter attacker, IFormation currentDefender ) {

		BaseFighter defender = currentDefender.getDefender( attacker );
		assert( defender != null );
		
		AttackInfo info = util.normalAttack( attacker, defender );		
		warSituation.put( attacker, defender, info );
		
		if( reduceHp( defender, info.getDamage() ) == true ){
			return true;
		}

		attacker.setSp( attacker.getSp() + SP_TO_ADD );
		
		if( !defender.isDie() ){
			if( info.getDamage() > 1 ){//防止不死之身之类的技能长久不结束
				defender.setSp( defender.getSp() + SP_TO_ADD );
			}
		
			if( info.isBlock() ){
				return doBlockAndCounterAttack( defender, attacker );
			}
		}
		return false;
	}

	
	/**

	 * 格挡并反击流程，不用考虑命中与暴击，同样也没考虑防御者身上的buff，暂时先这样，有需求在修改
	 * @param attacker
	 * @param defender
	 * @param defenderTeam
	 * @return
	 */
	private boolean doBlockAndCounterAttack( BaseFighter attacker, BaseFighter defender ) {
		
		int damage = util.calcCounterAttackDamage(attacker, defender);
		warSituation.putCounterAttackDamage( (int) damage );//伤害值
		return reduceHp( defender, damage );
	}
	
	private void pet() {
		Pet pet = attackers.getPet();
		if( pet != null ){
			//pet.run( this );
		}
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
		
		if( defender.isDie() ){//被攻击战士已经挂了			
			if( getFormation( defender ).isAllDie() ){
				return true;
			}
		}
		return false;
	}

	@Override
	public WarSituation getWarSituation() {
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
