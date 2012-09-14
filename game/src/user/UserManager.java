package user;

import game.packages.Packages;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
//		UserInfo oldUser = onlineUsers.get( user.getName() ); 
//		
//		if( oldUser != null ){//此玩家在线
//			oldUser.getConn().close();
//			code = ErrorCode.USER_HAS_LOGIN;
//		}
//		else{
//			code = db.get( user );
//			if( code == ErrorCode.SUCCESS )
//			{
//				if( user.getStatus() == UserStatus.LOGIN ){
//					if( doLogin( user ) != null ){//有人捷足先登了
//						code = ErrorCode.USER_HAS_LOGIN; 
//					}
//				}
//				else{
//					if( user.getStatus() == UserStatus.BAN ){
//						code = ErrorCode.USER_HAS_BAN;
//					}
//				}
//			}		
//			System.out.println( "在线人数" + onlineUsers.size() );
//		}
		code = db.get(user);
		if( code == ErrorCode.SUCCESS ){
			if( user.getStatus() == UserStatus.BAN ){
				code = ErrorCode.USER_HAS_BAN;
			}
			else if( user.getStatus() == UserStatus.LOGIN ){
				UserInfo oldUser = doLogin( user );
				if( oldUser != null && oldUser != user ){//二次登陆
					code = ErrorCode.USER_HAS_LOGIN;
					oldUser.getConn().close();
				}
			}
		}
		
		return code;
	}
	
	/**
	 * 玩家退出游戏，回写一些玩家的信息到数据库
	 * 
	 * 注意：
	 * 			如果是内部调用exit，必须确保玩家所发送的所有包信息都执行完成之后，才可调用exit，否则可能出现如下问题：
	 * 			玩家发送一个会损耗大量点券的包，并且已经从onlineUsers中取出该user对象，然后，内部调用exit，并
	 * 
	 * @param name			外层确保不会为null
	 * @return
	 * @throws IOException 
	 */
	public ErrorCode exit( UserInfo user ) throws IOException{
		ErrorCode code = ErrorCode.SUCCESS;

		if( onlineUsers.containsValue( user ) ){
			onlineUsers.remove( user.getName() );
			synchronized ( user ) {
				user.setLastLogoutTime( SystemTimer.currentTimeSecond() );
				code = db.update(user);
			}
		}
		return code;
	}
	
	/**
	 * 把玩家添加到程序中
	 * @param user
	 */
	private UserInfo doLogin( UserInfo user ){
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
	public ErrorCode run( UserInfo user, Packages pack, byte[] data ) {
		
		user.run(pack, data);
		
		return ErrorCode.SUCCESS;
	}
	public static void main(String[] args) {
		ConcurrentMap<String, Integer > map = new ConcurrentHashMap<String, Integer>();
		System.out.println( map.putIfAbsent( "a", 2));
		System.out.println( map.putIfAbsent( "a", 3));
		System.out.println( map.putIfAbsent( "a", 5));
		for( Entry<String, Integer> e : map.entrySet() ){
			System.out.println( e.getKey() + "=" + e.getValue() );
		}
	}
}
