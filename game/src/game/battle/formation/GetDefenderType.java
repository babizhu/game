package game.battle.formation;

import java.util.ArrayList;
import java.util.List;

import game.fighter.BaseFighter;

public enum GetDefenderType {
	ROW {
		@Override
		public
		List<BaseFighter> getDefender( BaseFighter defender, IFormation defeners ) {
			List<BaseFighter> ret = new ArrayList<BaseFighter>();
////			int row =  
////				List<BaseFighter> fighterList = defeners.getAllFighters();
//			for( BaseFighter f : fighterList ){
//				if( calcRow( f.getPosition() ) == row ){
//					ret.add( f );
//				}
//			}
			return ret;
		}
	},
	COL {
		@Override
		public
		List<BaseFighter> getDefender(BaseFighter defender, IFormation defeners ) {
			// TODO Auto-generated method stub
			xx();
			return null;
		}
	};
	void xx(){
		System.out.println( 3434);
	}
	
	/**
	 * 
	 * @param defender		普通攻击下应该估计的战士，按行按列
	 * @param defeners
	 * @return
	 */
	public abstract List<BaseFighter> getDefender( BaseFighter defender, IFormation defeners );
	public static void main(String[] args) {
		COL.getDefender( null, null);
	}
}
