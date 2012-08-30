package user;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import util.ErrorCode;
import util.SystemTimer;

/**
 * 用户管理类	单例
 * @author admin
 * 2012-8-20 下午04:09:39
 */
public class UserManager {
	//private final static Logger logger = LoggerFactory.getLogger(UserManager.class);
	
	private static final UserManager instance = new UserManager();
	public static final UserManager getInstance(){
		return instance;
	}
	private UserManager() {	}
	
	private final UserInfoDataProvider db = UserInfoDataProvider.getInstance();
	
	private Map<String,UserInfo> onlineUser = new ConcurrentHashMap<String, UserInfo>();
	

	/**
	 * 玩家尝试登陆<br>
	 * 目前只有三种情况 1、新玩家，2、正常登陆，3、被BAN玩家<br>
	 * 除了正常登陆需要一定处理以外，其他两种情况均可发送给客户端进行处理
	 * @param user
	 * @return
	 * @throws IOException 
	 */
	public ErrorCode login( UserInfo user ) throws IOException{
		if( user.getStatus() == UserStatus.LOGIN ){//防止反复登录
			return ErrorCode.USER_HAS_LOGIN;
		}
		if( user.getStatus() == UserStatus.NEW ){
			return ErrorCode.USER_NOT_FOUND;
		}
		if( user.)
		ErrorCode code = null;
		UserInfo oldUser = onlineUser.get( user.getName() ); 
		if( oldUser != null ){//此玩家在线
			//TODO 给老玩家发送退出包
			oldUser.getConn().close();
			oldUser.setConn( user.getConn() );
			user = oldUser;			
		}
		else{
			code = db.get( user );
			if( code == ErrorCode.SUCCESS )
			{
				doLogin( user );
			}		
			System.out.println( "在线人数" + onlineUser.size() );
		}
		return code;		
	}
	
	/**
	 * 玩家退出游戏
	 * @param user
	 * @return
	 * @throws IOException 
	 */
	public ErrorCode exit( UserInfo user ) throws IOException{
		ErrorCode code = ErrorCode.SUCCESS;
		if( user.getStatus() == UserStatus.LOGIN ){
			onlineUser.remove( user.getName() );
			user.setLastLogoutTime( SystemTimer.currentTimeSecond() );
			code = db.update(user);
		}
		if( user.getConn().isOpen() ){
			user.getConn().close();
		}
		System.out.println( "在线人数" + onlineUser.size() );
		return code;
	}
	
	/**
	 * 把玩家添加到程序中
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
		ErrorCode code = db.create( user ) ;
		if( code == ErrorCode.SUCCESS ){
			user.setStatus( UserStatus.LOGIN );
		}
//		doLogin( user );
		return code;
	}
	
	/**
	 * 显示所有的在线用户列表
	 */
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
