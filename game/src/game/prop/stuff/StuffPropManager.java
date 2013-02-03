package game.prop.stuff;


import game.prop.IpropManager;
import game.prop.PropDataProvider;
import game.prop.PropUnit;
import game.prop.cfg.PropTempletCfg;
import game.prop.templet.BasePropTemplet;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import util.ErrorCode;

public class StuffPropManager implements IpropManager  {

	
	/**
	 * short for templetId,Short for count
	 */
	private final Map<Short,Integer> stuffs;	
	private final PropDataProvider	db = PropDataProvider.getInstance();
	private final String uname;	

	public StuffPropManager( String uname ) {
		this.uname = uname;
		stuffs = db.getAllStuffs( uname );
	}

	@Override	
	/**
	 * 计算所需的格子
	 * @param unit
	 * @return
	 */
	public int calcNeedGridCount( PropUnit unit ){
		Integer count = stuffs.get( unit.getTemplet().getTempletId() );
		int needGrid = 0;
		if( count != null ){
			int stackCapacity = unit.getTemplet().getStackCapacity();
			int oldGrid = (int) Math.ceil( (float)count / stackCapacity );
			int newGrid = (int) Math.ceil( (float)(count+unit.getCount()) / stackCapacity );
			needGrid = newGrid - oldGrid;
		}
		else{
			needGrid = (int) Math.ceil( (float)unit.getCount() / unit.getTemplet().getStackCapacity() );
		}
		return needGrid;
	}
	
	@Override	
	/**
	 * 添加物品
	 * @param unit
	 * @param uname 
	 * @return
	 * 
	 * 注意：这里没有考虑背包格子数目的问题
	 */
	public ErrorCode add( PropUnit unit ){
		short templetId = unit.getTemplet().getTempletId();
		Integer count = stuffs.get( templetId );
		boolean isNew = (count==null);
		
		ErrorCode code = db.addStuff( templetId, unit.getCount(), uname, isNew );
		if( code == ErrorCode.SUCCESS ){
			if( !isNew ){
				count += unit.getCount();
			}
			stuffs.put( templetId, unit.getCount() );
		}
		return code;
	}
	
	@Override
	public ErrorCode checkPropIsEnough( PropUnit unit ){
		int needCount = unit.getCount();
		short templetId = unit.getTemplet().getTempletId();
		Integer c = stuffs.get( templetId );
		if( c == null ){
			return ErrorCode.PROP_NOT_FOUNTD;
		}
		if( c < needCount ){
			return ErrorCode.PROP_NOT_ENOUGH;
		}
		return ErrorCode.SUCCESS;
	}
	
	@Override	
	/**
	 * 移除物品，是否需要检测逻辑？
	 * @param unit
	 * @return
	 */
	public ErrorCode remove( PropUnit unit ){
		short templetId = unit.getTemplet().getTempletId();
		int result = stuffs.get( templetId ) - unit.getCount();
//		boolean isDelete = ( result == 0 );
		
		ErrorCode code = db.removeStuff( templetId, -result, uname );
		if( code != ErrorCode.SUCCESS ){
			return code;
		}
		if( result == 0 ){
			stuffs.remove( templetId );
		}
		else{
			stuffs.put( templetId, result );
		}
		return ErrorCode.SUCCESS;
		
	}
	
	public static void main(String[] args) {
		Map<Short,Short> s = new HashMap<Short, Short>();
		s.put( (short)1,(short)100);
		Short tt =  s.get((short)10);
		//short t =  s.get((short)10);
		System.out.println( tt);
		
	}

	@Override
	public int getGridCount() {
		int count = 0;
		for( Entry<Short,Integer> e : stuffs.entrySet() ){
			BasePropTemplet t = PropTempletCfg.getTempletById( e.getKey() );
			int stackCapacity = t.getStackCapacity();
			count += (int) Math.ceil( (float)e.getValue() / stackCapacity );
		}
		return count;
	}

	
}
