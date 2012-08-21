package user;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import util.ErrorCode;

/**
 * 用户管理类	单例
 * @author admin
 * 2012-8-20 下午04:09:39
 */
public class UserManager {
	
	private static final UserManager instance = new UserManager();
	public static final UserManager getInstance(){
		return instance;
	}
	private UserManager() {	}
	
	private Map<String,UserInfo> onlineUser = new ConcurrentHashMap<String, UserInfo>();
	

	public ErrorCode login( UserInfo user ){
		//TODO 从数据库获取数据
//		if( )
		onlineUser.put( user.getName(), user );
		return null;
		
	}
	
	public ErrorCode logout( UserInfo user ){
		//TODO 从数据库获取数据
//		if( )
		onlineUser.remove( user.getName() );
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder( "online user[\n");
		for( Entry<String, UserInfo> e : onlineUser.entrySet() ){
			sb.append( "[" );
			sb.append( e.getValue() );
			sb.append( "]\n" );
		}
		sb.append( "]" );
		return sb.toString();
	}
	
	
}
