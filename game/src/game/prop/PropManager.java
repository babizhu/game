package game.prop;

import game.bag.PropUnit;

import java.util.HashMap;
import java.util.Map;

import util.ErrorCode;


/**
 * 同步的思考
 * 
 * 	内部不要有外部的任何牵扯，加锁方式永远是：外部加锁，本类加锁
 * @author admin
 * 2012-10-19 下午05:13:29
 */
public class PropManager implements IBag{
	
	private Map<Long,BaseProp> props = new HashMap<Long, BaseProp>();
	private Map<Short,BaseProp> stuffs = new HashMap<Short, BaseProp>();
	
	public PropManager() {
		
	}

	@Override
	public ErrorCode remove(PropUnit unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorCode remove(PropUnit[] units) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorCode put(PropUnit unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorCode put(PropUnit[] units) {
		// TODO Auto-generated method stub
		return null;
	}
	
	ErrorCode levelUp( long propId ){
		return null;		
	}
	
	ErrorCode sell( long propId ){
		return null;		
	}
	
	ErrorCode buy( long propId ){
		return null;		
	}
	
	
	
}
