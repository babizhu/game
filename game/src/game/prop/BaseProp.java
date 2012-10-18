package game.prop;


public abstract class BaseProp implements IProp{

	/**
	 * 唯一ID
	 */
	long					id;
	
	/**
	 * 是否在背包里
	 */
	boolean					inBag;
	
	/**
	 * 数量
	 */
	int						count;
	

}
