package game.bag;

import game.prop.templet.BasePropTemplet;
import java.util.List;

import user.UserInfo;
import util.ErrorCode;

/**
 * 背包管理系统
 * 同步思考
 * 		此类不可避免在逻辑上会和装备管理类发生交互，例如删除某个装备，既需要从装备管理器中删除装备，也需在格子中删除
 * 	这里统一为先处理格子内的数据，再处理装备管理器内的数据
 * @author liukun
 * 2012-10-17 上午11:10:05
 */
public class Bag implements IBag {
	/**
	 * 包内的格子列表
	 */
	private List<BaseGrid> 			grids;
	
	/**
	 * 玩家
	 */
	private UserInfo				user;

	/**
	 * 背包格子上限
	 */
	private short		 			capacity;
	

	private final BagDataProvider 	db = BagDataProvider.getInstance();	

	public Bag( short capacity, UserInfo user ) {
		super();
		this.capacity = capacity;
		this.user = user;
		grids = db.getAllGridByUser( user.getName() );
	}
	
	@Override
	public synchronized ErrorCode remove( PropUnit unit ) {
		boolean isStuff = unit.getPropId() == 0;//注意，有可能要移除某装备但是不指定propId的情况（从背包中进行装备合成），这样的话，把装备到材料处理即可
		int total = getCount( isStuff, unit );
		if( total < unit.getCount() ){
			return ErrorCode.PROP_NOT_ENOUGH;
		}
		remove( isStuff, unit );
		return ErrorCode.SUCCESS;
	}

	private void remove( boolean isStuff, PropUnit unit ){
		if( isStuff ){//材料
			int remain = unit.getCount();
			int realRemove = 0;
			for( BaseGrid g : grids ){
				if( g.getTemplet() == unit.getTemplet() ){
					realRemove = g.remove( remain );
					remain -= realRemove;
					if( remain == 0 ){
						break;
					}
				}
			}
		}
		else{
			for( BaseGrid g : grids ){
				if( unit.getPropId() == g.getPropId() ){
					g.remove( 1 );
				}
			}
		}
			
	}
		
		

	/**
	 * 统计背包内某物品的数量
	 * 如果是装备（拥有唯一ID），则比较比较唯一ID，并返回1
	 * @param isStuff 
	 * @param templetId
	 */
	private int getCount( boolean isStuff, PropUnit unit ){
		int total = 0;
		if( isStuff ){
			for( BaseGrid g : grids ){
				if( g.getTemplet() == unit.getTemplet() ){
					total += g.getCount();
				}
			}
		}
		else{//道具
			for( BaseGrid g : grids ){
				if( unit.getPropId() == g.getPropId() ){
					return 1;
				}
			}
			
		}
		
		return total;
	}
	@Override
	public ErrorCode remove(PropUnit[] units) {
		
	}

	@Override
	public ErrorCode put( PropUnit unit ) {
		if( !canPut(unit) ){
			return ErrorCode.GRID_NOT_ENOUGH;
		}
		for( BaseGrid g : grids ){
			
			
		}
		return ErrorCode.SUCCESS;
	}

	@Override
	public ErrorCode put(PropUnit[] units) {
		for( PropUnit unit : units ){
			if( !canPut(unit) ){
				return ErrorCode.GRID_NOT_ENOUGH;
			}
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 计算物品所占的格子数
	 * @return
	 */
	private int calcGridCount(){
		int count = 0;
		for( BaseGrid g : grids ){
			int stackCapacity = g.getTemplet().getStackCapacity();
			count += Math.ceil( (float)g.getCount() / stackCapacity );
			
		}
		return count;
	}
	
	/**
	 * 判断是否背包内是否可容纳这些道具
	 * @param unit
	 * @return
	 */
	private boolean canPut( PropUnit unit ){
		int free = 0;
		for( BaseGrid g : grids ){
			if( g.getTemplet() == unit.getProp().getTemplet() ){
				free += g.getTemplet().getStackCapacity() - g.getCount();
			}
			if( free >= unit.getCount() ){
				return true;
			}			
		}
		int freeGrid = capacity - calcGridCount();//空闲的格子
		for( int i = 0; i < freeGrid; i++ ){
			free += unit.getProp().getTemplet().getStackCapacity();
			if( free >= unit.getCount() ){
				return true;
			}
		}
		return false;
	}
	public static void main(String[] args) {
		System.out.println( Math.ceil( (float)5 / 5 ) );
	}
}
