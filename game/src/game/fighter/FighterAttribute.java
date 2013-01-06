package game.fighter;


public enum FighterAttribute {

	ENEMY_HP(1){

		@Override
		public void run(BaseFighter fighter, int numToChange) {
			// TODO Auto-generated method stub
			
		}
		
	},
	HP(2) {
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
}
