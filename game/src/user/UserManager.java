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
	
	private final UserInfoDataProvider db = UserInfoDataProvider.getInstance();
	
	private Map<String,UserInfo> onlineUser = new ConcurrentHashMap<String, UserInfo>();
	

	/**
	 * 玩家尝试登陆
	 * @param user
	 * @return
	 */
	public void login( UserInfo user ){
		db.login( user );
		if( user.getStatus() == UserStatus.NEW ){
//			return ErrorCode.USER_NOT_FOUND;
		}
		else if( user.getStatus() == UserStatus.LOGIN ){
			doLogin( user );
			
		}
		else{
			
		}
		
		
	}
	
	/**
	 * 玩家退出游戏
	 * @param user
	 * @return
	 */
	public ErrorCode logout( UserInfo user ){
		//TODO 从数据库获取数据
//		if( )
		onlineUser.remove( user.getName() );
		return null;
	}
	
	/**
	 * 把玩家添加到程序逻辑中
	 * @param user
	 */
	private ErrorCode doLogin( UserInfo user ){
		onlineUser.put( user.getName(), user );
		return null;
		
	}
	/**
	 * 创建一个新玩家，玩家名以及其他基本属性由user指定
	 * @param user
	 * @return
	 */
	public ErrorCode create( UserInfo user ){
		
		doLogin( user );
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
