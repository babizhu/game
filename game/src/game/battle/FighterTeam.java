package game.battle;

import game.IFighterTeam;
import game.fighter.BaseFighter;

import java.util.ArrayList;
import java.util.List;

/**
 * 战斗团队，所有的战士副本在这里生成
 * @author liukun
 * 2012-11-13 上午10:52:22
 */
public class FighterTeam implements IFighterTeam {

	/**
	 * 战士列表
	 */
	private final List<BaseFighter> 		fighters;
	
	/**
	 * 宠物系统，类似"将神"里面的神龙
	 */
	private final Pet						pet;
	
	/**
	 * 会自动生成参战人员的副本列表
	 * @param fighterList
	 * @param pet
	 * @param isLeft			是否位于战场的左边（攻击方）
	 */
	public FighterTeam( List<BaseFighter> fighterList, Pet pet, boolean isLeft ) {
		if( fighterList == null || fighterList.size() == 0 ){
			throw new IllegalArgumentException( (isLeft == true ? "攻方" : "守方") + "战士列表为空或者数量为0" );
		}
		fighters = new ArrayList<BaseFighter>();
		for( BaseFighter f : fighterList ){
			BaseFighter newFighter = new BaseFighter( f, isLeft );
			fighters.add( newFighter );
		}
		this.pet = pet;
		
	}


	public List<BaseFighter> getFighters() {
		return fighters;
	}

	public Pet getPet() {
		return pet;
	}

	/**
	 * 是否所有的战士都已经死亡
	 * @return
	 */
	public boolean allDie(){
		for( BaseFighter f : fighters ){
			if( f.getHp() > 0 ){
				return false;
			}
		}
		return false;
	}	
	
	public static void main(String[] args) {
	}
}
