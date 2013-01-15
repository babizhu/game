package game.battle.auto;

import game.battle.Pet;
import game.battle.formation.ChooseFighters;
import game.battle.formation.IFormation;
import game.fighter.BaseFighter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Formation9 implements IFormation{

	/**
	 * 阵型的总人数
	 */
	private static final byte				TOTAL_COUNT = 9;
	private static final int 				COUNT_PER_ROW = 3;
	
	private List<BaseFighter> 				fighters;
	private Pet								pet;

	/**
	 * 按照位置从低到高进行排序，否则计算被攻击战士的时候可能出错
	 */
	public static final Comparator<BaseFighter> posComparator = new Comparator<BaseFighter>(){
		@Override
		public int compare( BaseFighter f1, BaseFighter f2 ) {
			return f1.getPosition() - f2.getPosition();
		}
	};
	
	
	/**
	 * 复制一份实例用于战斗，通常用于主线通关的mission的阵型
	 * @param formation
	 * @return
	 */
	public Formation9( IFormation formation ){
		List<BaseFighter> oldList = formation.getAllFighters();
		List<BaseFighter> clonesList = new ArrayList<BaseFighter>();
		for( BaseFighter bf : oldList ){
			BaseFighter clone = new BaseFighter( bf );
			clonesList.add( clone );
		}
		
		fighters = clonesList;
		pet = formation.getPet();//TODO 需要克隆一份
	}
	
	/**
	 * @param fighters
	 * @param isLeft
	 * @param pet
	 * 
	 *	如有必要，请在调用处对 fightersList 进行克隆处理
	 */
	public Formation9( List<BaseFighter> fightersList, boolean isLeft, Pet pet ) {
//		if( fightersList == null || fightersList.size() == 0 ){
//			throw new IllegalArgumentException( (isLeft == true ? "攻方" : "守方") + "战士列表为空或者数量为0" );
//		}
//		List<BaseFighter> clonesList = new ArrayList<BaseFighter>();
//		for( BaseFighter bf : fightersList ){
////			BaseFighter clone = new BaseFighter( bf );
//			if( !isLeft ){
//				formatForDefender( bf );
//			}
//		}
		
		fighters = fightersList;
		if( !isLeft ){
			for( BaseFighter bf : fighters ){
				formatForDefender( bf );
			}
			//由于存在镜面翻转，这里需要重新排序
			Collections.sort( fighters, posComparator );
		}
		this.pet = pet;
		//TODO有必要的话应该克隆

	}
	
	@Override
	public Pet getPet() {
		return pet;
	}
	
	
	/**
	 * 对防守方进行一系列改造，包括<br>
	 * 所有位置+9(九宫格战斗)<br>
	 * 防守方镜面翻转<br>
	 * 重新按照位置信息排序<br>
	 * 这个代码就应该在这里执行，而不应该放到BaseBattle中，因为这个是和阵型密切相关的
	 */
	private void formatForDefender( BaseFighter fighter ){
		fighter.setPosition( (byte) (fighter.getPosition() + TOTAL_COUNT) );
		byte position = fighter.getPosition();
		if( position % COUNT_PER_ROW == 0 ){
			position += 2;
		}
		else if( position % COUNT_PER_ROW == 2 ){
			position -= 2;
		}
		fighter.setPosition( position );//镜面翻转
		fighter.setLeft( false );
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
	public BaseFighter getBaseDefender( BaseFighter attacker ) {
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
		BaseFighter defender = getBaseDefender( attacker );
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
			if( !f.isDie() && getRow( f.getPosition() ) == row ){
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
	 * 技能攻击模式下，找出普通攻击下被攻击的战士对象
	 * @param attacker
	 * @return
	 */
	private List<BaseFighter> getFightersByNormal(BaseFighter attacker) {
		List<BaseFighter> ret = new ArrayList<BaseFighter>();
		ret.add( getBaseDefender( attacker ) );
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
		int row = position / COUNT_PER_ROW;
		row = row < COUNT_PER_ROW ? row : row - COUNT_PER_ROW;
		return row;
	}
	
	@Override
	public List<BaseFighter> getFighterOnEffect( BaseFighter attacker, ChooseFighters type ) {
		if( type == null ){
			return null;
		}
		switch( type ){
		case ROW:
			return getFightersByRow( attacker );
		case SELF:
			return getFightersBySelf( attacker );
		case MIN_HP:
			return getFighterByMinHp();
		case NORMAL_ATTACK:
			return getFightersByNormal( attacker );
		case ALL:
			return getAllFighters();
		default:
			break;
		}
		return null;
	}
	
	
	

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder( "{" );
		for( BaseFighter f : fighters ){
			sb.append( f.toSimpleString() );
			sb.append( "|" );
		}
		sb.append( " pet=" + pet + "}");
		return sb.toString();
	}

	public static void main(String[] args) {
		BaseFighter f = new BaseFighter();
		List<BaseFighter> fighters = new ArrayList<BaseFighter>();
		fighters.add( f );
		Formation9 ff = new Formation9(fighters, false, null);
		System.out.println( ff.getFighterOnEffect( null, null ));
	}
}
