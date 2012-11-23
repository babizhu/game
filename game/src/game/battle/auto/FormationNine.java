package game.battle.auto;

import game.battle.FighterTeam;
import game.battle.IFormation;
import game.fighter.BaseFighter;

import java.util.ArrayList;
import java.util.List;


public class FormationNine implements IFormation {

	public static final int CELL_COUNT = 9;//九宫格总的格子数
	public static final int COUNT_PER_ROW = 3;//每行的格子数
	public static final int MAX_NUMBER_IN_FORMATION = 5;//一个阵型最大的参战人数  
	
	public static final IFormation INSTANCE = new FormationNine();
	
	private FormationNine(){
		
	}
	
	/**
	 * 根据战士的位置计算此战士所在的阵型的行数
	 * @param position
	 * @return
	 */
	private int calcRow( byte position ){
		return position / COUNT_PER_ROW;
	}
	
	/**
	 * 根据战士的位置计算此战士所在的阵型的列数
	 * @param position
	 * @return
	 */
	private int calcCol( byte position ){
		return position % COUNT_PER_ROW;
	}
	
	
	@Override
	public BaseFighter normalAttackWho( BaseFighter attacker, FighterTeam defender ) {
		List<BaseFighter> rowList = null;
		int row = calcRow( attacker.getPosition() );
		int[] rows = getAttackSequenceRow( row );
		for( int i : rows ){
			rowList = getFightersByRow( i, defender );//获取本行被攻击的战士

			if( rowList.size() > 0 ){
				boolean defenderIsLeft = rowList.get(0).isLeft(); 
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

	private List<BaseFighter> getFightersByRow( int row, FighterTeam defender	) {

		List<BaseFighter> ret = new ArrayList<BaseFighter>();
		List<BaseFighter> fighterList = defender.getFighters();
		for( BaseFighter f : fighterList ){
			if( calcRow( f.getPosition() ) == row ){
				ret.add( f );
			}
		}
		return ret;
	}

	@Override
	public List<BaseFighter> getFightersByRow( BaseFighter attacker, FighterTeam defender	) {
		int row = calcRow( attacker.getPosition() );
		return getFightersByRow( row, defender );
		
	}

	@Override
	public List<BaseFighter> getFightersByCol( BaseFighter attacker, FighterTeam defender	) {
		int col = calcCol( attacker.getPosition() );
		
		List<BaseFighter> ret = new ArrayList<BaseFighter>();
		List<BaseFighter> fighterList = defender.getFighters();
		for( BaseFighter f : fighterList ){
			if( calcCol( f.getPosition() ) == col ){
				ret.add( f );
			}
		}
		return ret;
	}

	@Override
	public List<BaseFighter> getFightersByCross( FighterTeam defender ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BaseFighter> getFightersByX( FighterTeam defender ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxFighterNmuberByLevel(int level) {
		// TODO Auto-generated method stub
		return 0;
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
}
