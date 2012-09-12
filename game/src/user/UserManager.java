package user;

import game.packages.Packages;

import java.io.IOException;
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
	private ConcurrentHashMap<String,UserInfo> onlineUsers = new ConcurrentHashMap<String, UserInfo>();
	

	/**
	 * 玩家尝试登陆<br>
	 * 目前只有三种情况 1、新玩家，2、正常登陆，3、被BAN玩家<br>
	 * 除了正常登陆需要一定处理以外，其他两种情况均可发送给客户端进行处理
	 * @param user
	 * @return
	 * @throws IOException 
	 */
	public ErrorCode login( UserInfo user ) throws IOException{
		
		ErrorCode code;
		UserInfo oldUser = onlineUsers.remove( user.getName() ); 
		
		if( oldUser != null ){//此玩家在线
			//TODO 给老玩家发送退出包
			//user.copy( oldUser );
			
			oldUser.setStatus( UserStatus.GUEST );
			oldUser.getConn().close();
			code = ErrorCode.SUCCESS;
		}
		else{
			code = db.get( user );
			if( code == ErrorCode.SUCCESS )
			{
				doLogin( user );
			}		
			System.out.println( "在线人数" + onlineUsers.size() );
		}
		return code;
	}
	
	/**
	 * 玩家退出游戏
	 * @param user
	 * @return
	 * @throws IOException 
	 */
	public ErrorCode exit( String name ) throws IOException{
		ErrorCode code = ErrorCode.SUCCESS;
		if( name == null ){
			return ErrorCode.USER_NOT_LOGIN; 
		}
		UserInfo user = onlineUsers.remove( name );
		if( user != null ){
		
			synchronized ( user ) {
				if( user.getStatus() == UserStatus.LOGIN ){
					user.setLastLogoutTime( SystemTimer.currentTimeSecond() );
					
					code = db.update(user);
//					user.setStatus( UserStatus.GUEST );//必须放在db.update(user);之后，否则数据库中的玩家状态就变成GUEST了
				}
			}
		}
		System.out.println( "在线人数" + onlineUsers.size() );
		return code;
	}
	
	/**
	 * 把玩家添加到程序中
	 * @param user
	 */
	private UserInfo doLogin( UserInfo user ){
		user.setStatus( UserStatus.LOGIN );
		return onlineUsers.putIfAbsent( user.getName(), user );
//		return ErrorCode.SUCCESS;
		
	}
	/**
	 * 创建一个新玩家，玩家名以及其他基本属性由user指定
	 * @param user
	 * @return
	 */
	public ErrorCode create( UserInfo user ){
		ErrorCode code = db.create( user ) ;
//		if( code == ErrorCode.SUCCESS ){
//			//user.setStatus( UserStatus.LOGIN );
//		}
//		doLogin( user );
		return code;
	}
	
	/**
	 * 显示所有的在线用户列表
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder( "online user[\n");
		for( Entry<String, UserInfo> e : onlineUsers.entrySet() ){
			sb.append( "[" );
			sb.append( e.getValue() );
			sb.append( "]\n" );
		}
		sb.append( "]" );
		return sb.toString();
	}
	/**
	 * @param string
	 * @return
	 * 
	 * 如果玩家不在线呢？
	 */
	public UserInfo getUserByName(String name) {
		return onlineUsers.get( name );
	}
	
	/**
	 * 之所以要从这里运行run方法，主要是为了保证外层不再拥有user信息，<br>
	 * 所有的user信息都是从onlineUsers中获取，这样可以缩小user被发布的范围，增加线程安全性
	 * @param name
	 * @param pack
	 * @param data
	 * @return
	 */
	public ErrorCode run( String name, Packages pack, byte[] data ) {
		
		if( name == null ){
			if( pack != Packages.USER_CREATE || pack != Packages.USER_LOGIN ){
				return ErrorCode.USER_NOT_LOGIN;
			}
		}
		UserInfo user = onlineUsers.get( name );
		if( user != null ){
			user.run(pack, data);
		}
		else{
			return ErrorCode.USER_NOT_FOUND;
		}
		return ErrorCode.SUCCESS;
	}
}
