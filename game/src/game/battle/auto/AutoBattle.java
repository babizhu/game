package game.battle.auto;

import game.battle.BattleBase;
import game.battle.IBattleUtil;
import game.battle.Pet;
import game.battle.buff.BuffRunPoint;
import game.battle.formation.IFormation;
import game.battle.formula.Formula;
import game.battle.skill.SkillEffect;
import game.battle.skill.SkillTemplet;
import game.fighter.FighterBase;
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
public class AutoBattle extends BattleBase {

	private static final Logger 		logger = LoggerFactory.getLogger( AutoBattle.class );
	private static final IBattleUtil	util = AutoBattleUtil.getInstance();

	private static final int 			SKILL_ATTACK_NEED_SP = 1000;
	public static final int 			SP_TO_ADD = 50;		
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
	private final List<FighterBase>		allFighters = new ArrayList<FighterBase>();
	
	private int 						currentRound = 0;
	
	/**
	 * 胜负结果
	 */
	private boolean 					attackerIsWin;
	
	/**
	 * 战况信息
	 */
	private BattleSituation				battleSituation = new BattleSituation( 1024 );	

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
		
		allFighters.addAll( attackers.getAllFighters() );
		allFighters.addAll( defenders.getAllFighters() );		
		Collections.sort( allFighters, util.getOrderComparator() );
	}

	/**
	 * 根据传入的战士获取相应的阵形
	 * @param fighter
	 * @param isFriend	
	 * 			true：输入战士这一边的阵形		false:输入战士敌对的阵形
	 * @return
	 */
	private IFormation getFormation( FighterBase fighter, boolean isFriend ){
		if( isFriend ){
			return fighter.isLeft() ?  attackers : defenders;
		}
		return fighter.isLeft() ? defenders : attackers;
	}
	
	@Override
	public void run() {
		boolean isEnd = false;
		while( !isEnd ){
			pet();
			battleSituation.putRoundFlag();
			for( FighterBase currentAttacker : allFighters ){
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
				
				IFormation currentDefenders;
				//考虑混乱的状态
				currentDefenders = getFormation( currentAttacker, currentAttacker.isChaos() );
				
				if( currentAttacker.getSp() >= SKILL_ATTACK_NEED_SP ){
					if( doSkillAttacks( currentAttacker, currentDefenders  ) ){
						isEnd = true;
						break;
					}
				}
				else{
					if( doNormalAttack( currentAttacker, currentDefenders ) ){
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
	
	
	private boolean doSkillAttacks( FighterBase attacker, IFormation currentDefenderTeam ) {
		
		SkillTemplet templet = attacker.getSkillTemplet();
		
		List<FighterBase> enemys = currentDefenderTeam.getFighterOnEffect( attacker, templet.getEnemys() );
		List<FighterBase> friends = getFormation( attacker, true ).getFighterOnEffect( attacker, templet.getFriends() );
		byte count = (byte) ((enemys == null ? 0 : enemys.size()) + (friends == null ? 0 : friends.size()));
		
		battleSituation.putSkillAttackPrefix( attacker, templet.getId(), count );
		
		if( enemys != null ){
			for( FighterBase f : enemys ){
				battleSituation.putFighter( f.getPosition() );
				if( doSkillAttack( attacker, f, templet.getEffectOnEnemy() ) )//此战士所在团队挂了
				{
					return true;
				}
			}
		}
		/*****************************************************************************************/
		if( friends != null ){
			for( FighterBase f : friends ){
				battleSituation.putFighter( f.getPosition() );
				doSkillAttack( attacker, f, templet.getEffectOnFriend() );
			}
		}
		return false;		
	}
	
	/**
	 * @param attacker		发技能的战士
	 * @param defender		受此技能影响的具体的某个战士
	 * @param effects		技能影响的内容 
	 * @see game.battle.skill.SkillEffect
	 * @return
	 * 				true	此战士挂了
	 * 				false	此战士未死
	 * 
	 *	注意：所有的SkillEffect中，ENEMY_HP必须排在最前面，后面的其他技能效果都依赖于ENEMY_HP是否命中
	 */
	private boolean doSkillAttack( FighterBase attacker, FighterBase defender, List<SkillEffect> effects ){
		boolean isHit = true;
		battleSituation.putEffectCount( (byte) effects.size() );
		for( SkillEffect se : effects ){
			if( se.getAttribute() == FighterAttribute.SUB_HP ){//和其他属性不同，技能攻击HP的需要特殊处理
				AttackInfo info = util.calcAttackInfo( attacker, defender, se.getFormula(), se.getArguments() );
				battleSituation.putSkillInfo( se.getAttribute(), info );
				
				if( info.isHit() && reduceHp( defender, info.getDamage() ) == true ){
					return true;
				}
				isHit = info.isHit();
			}
			else{
				if( isHit && !defender.isDie() ){
					int numberToChange = se.getFormula().run( attacker, defender, se.getArguments() );		
					se.getAttribute().run( defender, numberToChange );
					battleSituation.putSkillInfo( se.getAttribute(), numberToChange );
				}
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
	private boolean doNormalAttack( FighterBase attacker, IFormation currentDefender ) {

		FighterBase defender = currentDefender.getBaseDefender( attacker );
		assert( defender != null );
		
		AttackInfo info = util.calcAttackInfo( attacker, defender, Formula.NormalAttackFormula, null );
		battleSituation.putNormalAttack( attacker, defender, info );
		
		if( reduceHp( defender, info.getDamage() ) == true ){
			return true;
		}

		if( info.isHit() ){//未命中，不存在反击，这个逻辑可根据实际情况进行修改
			attacker.setSp( attacker.getSp() + SP_TO_ADD );
			
			if( !defender.isDie() ){
				if( info.getDamage() > 1 ){//防止不死之身之类的技能长久不结束
					defender.setSp( defender.getSp() + SP_TO_ADD );
				}
			
				if( info.isBlock() ){
					return doBlockAndCounterAttack( defender, attacker );
				}
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
	private boolean doBlockAndCounterAttack( FighterBase attacker, FighterBase defender ) {		
		int damage = util.calcCounterAttackDamage( attacker, defender );
		battleSituation.putCounterAttackDamage( (int) damage );//伤害值
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
	public boolean reduceHp( FighterBase defender, int damage ){
		defender.setHp( defender.getHp() - damage );
		
		if( defender.isDie() ){//被攻击战士已经挂了			
			if( getFormation( defender, true ).isAllDie() ){
				return true;
			}
		}
		return false;
	}

	@Override
	public BattleSituation getBattleSituation() {
		return battleSituation;
	}
	
	@Override
	public boolean getAttackerIsWin() {
		return attackerIsWin;
	}
	
	public static void main(String[] args) {
		
	}
}
