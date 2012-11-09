package game.prop;

import game.bag.PropUnit;

import java.util.HashMap;
import java.util.Map;

import util.ErrorCode;


/**
 * 同步的思考
 * 
 * 	内部不要有外部的任何牵扯，单纯的保持道具的数据，并和数据库打交道
 * 	加锁方式永远是：外部加锁，本类加锁
 * 
 * @author liukun
 * 2012-10-19 下午05:13:29
 */
public class PropDataManager implements IBag{
	
	/**
	 * long for 装备Id
	 */
	private Map<Long,BaseProp> props = new HashMap<Long, BaseProp>();//不可重叠道具（通常指装备），道具具备唯一ID
	
	/**
	 * short for 模板Id
	 */
	private Map<Short,BaseProp> stuffs = new HashMap<Short, BaseProp>();//可重叠道具（通常指材料），道具不具备唯一ID
	
	public PropDataManager() {
		
	}

	@Override
	public ErrorCode remove( PropUnit unit ) {
		if( unit.getPropId() != 0 ){
			BaseProp p = props.get( unit.getPropId() );
		}
		else{
			
		}
		return null;
	}

	@Override
	public ErrorCode remove( PropUnit[] units ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorCode put( PropUnit unit ) {
		return null;
	}

	@Override
	public ErrorCode put( PropUnit[] units ) {
		// TODO Auto-generated method stub
		return null;
	}	

}
