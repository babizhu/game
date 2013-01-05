package game.fighter;


public enum FighterAttribute {

	ENEMY_HP{

		@Override
		public void run(BaseFighter fighter, int numToChange) {
			// TODO Auto-generated method stub
			
		}
		
	},
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
