package game.fighter;


public interface IFighter {
	
	/**
	 * 给战士穿上装备
	 * @param propId	要穿的装备ID
	 * @return
	 */
	//ErrorCode dress( long oldPropId, long newPropId );
	
	/**
	 * 返回该战士的等级
	 * @return
	 */
	short getLevel();

}
