package game.fighter;

public enum FighterAttribute {

	HP {
		@Override
		void change(BaseFighter fighter, int numToChange) {
			fighter.setHp( fighter.getHp() + numToChange );
			
		}
	},SP {
		@Override
		void change(BaseFighter fighter, int numToChange) {
			fighter.setSp( fighter.getSp() + numToChange );
			
		}
	};
	
	abstract void change( BaseFighter fighter, int numToChange );
}
