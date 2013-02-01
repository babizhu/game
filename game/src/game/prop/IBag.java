package game.prop;

public interface IBag {
	/**
	 * 计算放入当前物品之后，所需要的格子数
	 * @param unit		要放入的物品
	 * @return
	 */
	int calcNeedGridCount( PropUnit unit );

}
