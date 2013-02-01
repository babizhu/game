package game.prop.stuff;


import game.prop.IBag;
import game.prop.PropDataProvider;
import game.prop.PropUnit;

import java.util.HashMap;
import java.util.Map;

import util.ErrorCode;

public class StuffPropManager implements IBag  {

	
	/**
	 * short for templetId,Short for count
	 */
	private final Map<Short,Integer> stuffs;	
	private final PropDataProvider	db = PropDataProvider.getInstance();

	public StuffPropManager( String uname ) {
		stuffs = db.getAllStuffs( uname );
	}

	@Override	
	/**
	 * 计算所需的格子
	 * @param unit
	 * @return
	 */
	public int calcNeedGridCount( PropUnit unit ){
		Integer c = stuffs.get( unit.getTemplet().getTempletId() );
		int needGrid = 0;
		if( c != null ){
			int oldGrid = (int) Math.ceil( (float)c / unit.getTemplet().getStackCapacity() );
			int newGrid = (int) Math.ceil( (float)(c+unit.getCount()) / unit.getTemplet().getStackCapacity() );
			needGrid = newGrid - oldGrid;
		}
		else{
			needGrid = (int) Math.ceil( (float)unit.getCount() / unit.getTemplet().getStackCapacity() );
		}
		return needGrid;
	}
	
	/**
	 * 添加物品
	 * @param unit
	 * @param uname 
	 * @return
	 * 
	 * 注意：这里没有考虑背包格子数目的问题
	 */
	public ErrorCode add( PropUnit unit, String uname ){
		boolean isNew = !stuffs.containsKey( unit.getTemplet().getTempletId() );
		return db.addStuff( unit, uname, isNew );
	}
	
	/**
	 * 移除物品
	 * @param unit
	 * @return
	 */
	ErrorCode remove( PropUnit unit ){
		return ErrorCode.SUCCESS;
	}
	
	public static void main(String[] args) {
		Map<Short,Short> s = new HashMap<Short, Short>();
		s.put( (short)1,(short)100);
		Short tt =  s.get((short)10);
		//short t =  s.get((short)10);
		System.out.println( tt);
		
	}


}
