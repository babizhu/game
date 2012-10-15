package game.bag;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.ErrorCode;

public class Bag implements IBag {

	/**
	 * 包内的格子列表
	 */
	private List<BaseGrid> 			grids;
//	private UserInfo				user;
	/**
	 * 玩家用户名
	 */
	private String					uname;
	
	/**
	 * 背包格子上限
	 */
	private final short 			capacity;
	

	private final BagDataProvider 	db = BagDataProvider.getInstance();	

	public Bag( short capacity, String uname ) {
		super();
		this.capacity = capacity;
		this.uname = uname;
		grids = db.getAllGridByUser( uname );
	}
	
	@Override
	public synchronized ErrorCode remove(PropUnit unit) {
		int total = getCount( unit.getTempletId() );
		if( total < unit.getCount() ){
			return ErrorCode.PROP_NOT_ENOUGH;
		}
		else{	
			int remain = total;
			int realRemove = 0;
			for( BaseGrid g : grids ){
				
				realRemove = g.remove( remain );
				remain -= realRemove;
				if( remain == 0 ){
					break;
				}
			}
			return ErrorCode.SUCCESS;
		}
		
	}

	/**
	 * 统计背包内某物品的数量
	 * @param templetId
	 */
	private int getCount( short templetId ){
		int total = 0;
		for( BaseGrid g : grids ){
			if( g.getTemplet().getTempletId() == templetId ){
				total += g.getCount();
			}
		}
		return total;
	}
	@Override
	public Set<BaseGrid> remove(PropUnit[] units) {
		return null;
	}

	@Override
	public Set<BaseGrid> put(PropUnit unit) {
		if( grids.size() > capacity ){
			throw new IllegalAccessError();
		}
		return null;
	}

	@Override
	public Set<BaseGrid> put(PropUnit[] units) {
		return null;
	}

}
