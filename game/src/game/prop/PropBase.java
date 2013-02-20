package game.prop;


public abstract class PropBase implements IProp{

	
	/**
	 * 是否在背包里
	 */
	private boolean					inBag = true;
	
	
	public boolean isInBag() {
		return inBag;
	}

	public void setInBag(boolean inBag) {
		this.inBag = inBag;
	}
	
	
}
