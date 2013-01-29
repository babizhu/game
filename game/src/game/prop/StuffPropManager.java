package game.prop;


import java.util.HashMap;
import java.util.Map;
import util.ErrorCode;

public class StuffPropManager implements IBag  {

	Bag		bm;
	/**
	 * short for templetId,Short for count
	 */
	Map<Short,Short> stuffs = new HashMap<Short, Short>(); 

	public StuffPropManager( String uname ) {
		// TODO Auto-generated constructor stub
	}
	
	public int getGridCount(){
		return 10;
	}
	
	@Override
	public ErrorCode put( PropUnit unit ){
		if (bm.getFreeGridCount() < calcNeedGridCount(unit)) {
			return ErrorCode.GRID_NOT_ENOUGH;
		}

		short templetId = unit.getTemplet().getTempletId();
		Short c = stuffs.get(templetId);
		if (c == null) {
			stuffs.put((short) templetId, unit.getCount());
			// TODO 同步数据库
		} else {
			c = (short) (c + unit.getCount());
			stuffs.put(templetId, c);
			// TODO 同步数据库
		}

		return ErrorCode.SUCCESS;
	}

	@Override
	public ErrorCode remove(PropUnit unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorCode remove( PropUnit[] units ) {
		for( PropUnit unit : units ){
			put( unit );
		}
		return ErrorCode.SUCCESS;
	}

	@Override
	public ErrorCode put(PropUnit[] units) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 计算所需的格子
	 * @param unit
	 * @return
	 */
	private int calcNeedGridCount( PropUnit unit ){
		Short c = stuffs.get( unit.getTemplet().getTempletId() );
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
	
	
	public static void main(String[] args) {
		Map<Short,Short> s = new HashMap<Short, Short>();
		s.put( (short)1,(short)100);
		Short tt =  s.get((short)10);
		//short t =  s.get((short)10);
		System.out.println( tt);
		
	}

}
