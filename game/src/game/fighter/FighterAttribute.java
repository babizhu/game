package game.fighter;


import java.util.HashMap;
import java.util.Map;


public enum FighterAttribute {

	SUB_HP(1){

		@Override
		public void run(BaseFighter fighter, int numToChange) {
			new Exception( "No implent this method!——ENEMY_HP.run()" );
		}
		
	},
	ADD_HP(2) {
		@Override
		public
		void run(BaseFighter fighter, int numToChange) {
			
			fighter.setHp( fighter.getHp() + numToChange );
			
		}
	},SP(3) {
		@Override
		public
		void run(BaseFighter fighter, int numToChange) {
			fighter.setSp( fighter.getSp() + numToChange );
			
		}
	};
	
	public abstract void run( BaseFighter fighter, int numToChange );

	private final byte number;
	FighterAttribute( int n ) {
		number = (byte) n;
	}
	public byte toNumber() {
		return number;
	}
	private static final Map<Byte, FighterAttribute> numToEnum = new HashMap<Byte, FighterAttribute>();
	static{
		for( FighterAttribute a : values() ){
			numToEnum.put( a.number, a );
		}
	}
	public static FighterAttribute fromNumber( byte n ){
		return numToEnum.get( n );
		
	}
}
