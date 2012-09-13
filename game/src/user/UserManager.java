package user;

import game.packages.Packages;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.xsocket.connection.INonBlockingConnection;

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
		UserInfo oldUser = onlineUsers.get( user.getName() ); 
		
		
		if( oldUser != null ){//此玩家在线
			
			user.setcon
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
	public ErrorCode exit( String name ) throws IOException{
		ErrorCode code = ErrorCode.SUCCESS;


		UserInfo user = onlineUsers.remove( name );
		if( user == null ){
			//按道理说name！=null，这里就不会等于null，考虑什么情况下会出现con的attachment有name值，而这里却没有user的情况？？？？？？？？？？？？
			return ErrorCode.USER_NOT_LOGIN; 

		}
		synchronized ( user ) {
			user.setLastLogoutTime( SystemTimer.currentTimeSecond() );
			code = db.update(user);
//				user.setStatus( UserStatus.GUEST );//必须放在db.update(user);之后，否则数据库中的玩家状态就变成GUEST了
//			if( user.getStatus() == UserStatus.LOGIN ){
//			}
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
	 * 
	 * @param con
	 * @param pack
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	public ErrorCode run( INonBlockingConnection con, Packages pack, byte[] data ) throws IOException {
		
		ByteBuffer buf = ByteBuffer.wrap( data );
		String name = (String) con.getAttachment();
		if( name == null ){
			
			if( pack == Packages.USER_CREATE || pack == Packages.USER_LOGIN ){
				UserInfo user = new UserInfo( con );
				pack.run( user, buf );
			}
			else{
				return ErrorCode.USER_NOT_LOGIN;
			}
		}
		else{
			if( pack == Packages.USER_CREATE || pack == Packages.USER_LOGIN ){
				return ErrorCode.USER_HAS_LOGIN; 
			}
			UserInfo user = onlineUsers.get( name );
			pack.run(user, buf);
		}
		return ErrorCode.SUCCESS;
	}
}
