package game.prop;


public abstract class BaseProp implements IProp{

	/**
	 * 唯一ID
	 */
	private long					id;
	
	/**
	 * 是否在背包里
	 */
	private boolean					inBag;
	
	/**
	 * 数量
	 */
	private int						count;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isInBag() {
		return inBag;
	}

	public void setInBag(boolean inBag) {
		this.inBag = inBag;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	

}
