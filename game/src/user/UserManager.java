package user;

import game.packages.Packages;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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

//		onlineUsers.remove( user.getName() );
		UserInfo user = onlineUsers.get( name );//正常情况下这里不可能为null，无需测试
		synchronized ( user ) {
			user.setLastLogoutTime( SystemTimer.currentTimeSecond() );
			code = db.update(user);
			user.setCon( null );
		}
		
		return code;
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
	 * 从数据库获取玩家信息,不管是否在线，只要该玩家确实在数据库中存在，就尽力保存到内存当中来，是否在线无所谓
	 * @param string
	 * @return
	 * 
	 */
	public UserInfo getUserByName(String name) {
		if( onlineUsers.get( name ) == null ){
			UserInfo user = new UserInfo( null, name );
			ErrorCode code = db.get( user );
			if( code != ErrorCode.SUCCESS ){
				return null;
			}
			onlineUsers.putIfAbsent( name, user );
			
		}
		return onlineUsers.get( name );
	}
	
	/**
	 * 之所以要从这里运行run方法，主要是为了保证外层不再拥有user信息，<br>
	 * 所有的user信息都是从onlineUsers中获取，这样可以缩小user被发布的范围，增加线程安全性
	 * @param name
	 * @param pack
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	public ErrorCode run( String name, Packages pack, byte[] data ) throws IOException {
		
//		//user.run(pack, data);
		
		UserInfo user = getUserByName(name);
		if( user != null ){
			ByteBuffer buf = ByteBuffer.wrap( data );
			pack.run( user, buf );
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 成功的登陆需要注意以下条件：
	 * 1、此连接(con)尚未登陆
	 * 2、此用户名存在于数据库中
	 * 3、此玩家的状态是可登陆的状态
	 * 4、此玩家尚未登陆（不同连接）
	 * 
	 * 
	 * @param con
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public ErrorCode login( INonBlockingConnection con, ByteBuffer buf ) throws IOException {
		if( con.getAttachment() != null ){
			return ErrorCode.USER_HAS_LOGIN;
		}
		
		String name = util.BaseUtil.decodeString( buf );

		UserInfo user = getUserByName( name );
		if( user == null ){
			return ErrorCode.USER_NOT_FOUND;
		}
		synchronized (user) {
			if( user.getStatus() == UserStatus.BAN ){
				return ErrorCode.USER_HAS_BAN;
			}
			if( user.getCon() != null ){//二次登陆
				user.getCon().close();
				return ErrorCode.USER_HAS_LOGIN;//请客户的等待500ms后重试
			}
			user.setCon(con);
			con.setAttachment( name );			
			
		}
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
