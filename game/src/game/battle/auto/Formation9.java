package game.battle.auto;

import java.util.ArrayList;
import java.util.List;

import game.battle.Pet;
import game.battle.formation.ChooseFighters;
import game.battle.formation.IFormation;
import game.fighter.BaseFighter;

public class Formation9 implements IFormation{

	/**
	 * 阵型的总人数
	 */
	static final byte 						TOTAL_COUNT = 9;
	private static final int 				COUNT_PER_ROW = 3;
	
	private List<BaseFighter> 				fighters;
	//private boolean							isLeft;
	private Pet								pet;

	
	@Override
	public Pet getPet() {
		return pet;
	}
	/**
	 * 利用拷贝构造函数，来深度拷贝一个战士列表
	 * @param fighters
	 * @param isLeft
	 * @param pet
	 */
	public Formation9( List<BaseFighter> fighters, boolean isLeft, Pet pet ) {
		if( fighters == null || fighters.size() == 0 ){
			throw new IllegalArgumentException( (isLeft == true ? "攻方" : "守方") + "战士列表为空或者数量为0" );
		}
		fighters = new ArrayList<BaseFighter>();
		for( BaseFighter f : fighters ){
			BaseFighter newFighter = new BaseFighter( f, isLeft );
			fighters.add( newFighter );
		}
		this.pet = pet;
	}
	
	@Override
	/**
	 * 是否所有的战士都已经死亡
	 * @return
	 */
	public boolean isAllDie(){
		for( BaseFighter f : fighters ){
			if( !f.isDie() ){
				return false;
			}
		}
		return true;
	}

	@Override
	public List<BaseFighter> getAllFighters() {
		return fighters;
	}
	
	@Override
	public BaseFighter getDefender( BaseFighter attacker ) {
		boolean defenderIsLeft = !attacker.isLeft(); 
		List<BaseFighter> rowList = null;
		int row = getRow( attacker.getPosition() );
		int[] rows = getAttackSequenceRow( row );
		for( int i : rows ){
			rowList = getFightersByRow( i );//获取本行被攻击的战士

			if( rowList.size() > 0 ){
				if( defenderIsLeft ){
					return rowList.get( rowList.size() - 1 );//取最后一个
				}
				else{
					return rowList.get( 0 );//取第一个即可
				}
			}			
		}
		return null;
	}

	/**
	 * 按行获取战士
	 * @param row
	 * @return
	 */
	private List<BaseFighter> getFightersByRow( BaseFighter attacker ) {
		BaseFighter defender = getDefender( attacker );
		int row = getRow( defender.getPosition() );
		return getFightersByRow( row );
	}
	
	/**
	 * 按行获取战士
	 * @param row
	 * @return
	 */
	private List<BaseFighter> getFightersByRow( int row ) {
		List<BaseFighter> ret = new ArrayList<BaseFighter>();
		for( BaseFighter f : fighters ){
			if( getRow( f.getPosition() ) == row ){
				ret.add( f );
			}
		}
		return ret;
	}
	
	/**
	 * 获取自己
	 * @param fighter
	 * @return
	 */
	private List<BaseFighter> getFightersBySelf( BaseFighter fighter ) {
		List<BaseFighter> ret = new ArrayList<BaseFighter>();
		ret.add( fighter );
		return ret;
	}
	
	/**
	 * 找出血量最少的战士
	 * @return
	 */
	private List<BaseFighter> getFighterByMinHp() {
		List<BaseFighter> ret = new ArrayList<BaseFighter>();
		BaseFighter minHp = null;
		for( BaseFighter f : fighters ){
			if( f.getHp() > 0 ){
				if( minHp == null ){
					minHp = f;
				}
				else if( f.getHp() < minHp.getHp() ){
					minHp = f;
				}
			}
		}
		ret.add( minHp );
		return ret;
	}
	/**
	 *  攻击目标判定的"行"顺序：
	 *  
	 *	1、员工首先攻击自己同一横排的最前列对手；
	 *	2、当自己的同一横排无对手时，首先攻击相邻排的对手；
	 *	3、当拥有两个相邻排的对手时，首先攻击上排的对手（当自己处于中间横排时，首先攻击上方的对手）；

	 * @param row
	 * @return
	 */

	private int[] getAttackSequenceRow( int row ){
		int[] remain = new int[COUNT_PER_ROW];
		remain[0] = row;
		switch( row ){
			case 0:
				remain[1] = 1;
				remain[2] = 2;
				break;
			case 1:
				remain[1] = 0;
				remain[2] = 2;
				break;
			default:
				remain[1] = 1;
				remain[2] = 0;
				break;
		}
		return remain;

	}
	
	private int getRow(byte position) {
		return position / COUNT_PER_ROW;
	}
	
	@Override
	public List<BaseFighter> getFighterOnEffect( BaseFighter attacker, ChooseFighters type ) {
		switch( type ){
		case ROW:
			return getFightersByRow( attacker );
		case SELF:
			return getFightersBySelf( attacker );
		case MIN_HP:
			return getFighterByMinHp();
		}
		return fighters;
	}
}
