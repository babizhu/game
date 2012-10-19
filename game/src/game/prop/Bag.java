package game.prop;


public class Bag {

	/**
	 * 背包容量
	 */
	private short		 			capacity;
	


	public Bag( short capacity ) {
		super();
		this.capacity = capacity;
	}
	/**
	 * 此背包内所有道具管理器的实例，依据这个可以计算当前背包的空闲格子，即：<br>
	 * 所有道具的管理器内所有的道具除以上限并向上取整，即可获取所需格子数量
	 */
	//List<>
	public int getFreeGridCount() {
		// TODO Auto-generated method stub
		int use = 10;
		int free = capacity - use;
		return free;
	}
	

}
