package game.fighter;


public enum FighterAttribute {

	HP {
		@Override
		public
		void run(BaseFighter fighter, int numToChange) {
			
			fighter.setHp( fighter.getHp() + numToChange );
			
		}
	},SP {
		@Override
		public
		void run(BaseFighter fighter, int numToChange) {
			fighter.setSp( fighter.getSp() + numToChange );
			
		}
	};
	
	public abstract void run( BaseFighter fighter, int numToChange );


}
