package user;

import game.events.EventManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map.Entry;
import java.util.Random;
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
	 * 			
	 * 
	 * @param name			外层确保不会为null
	 * @return
	 * @throws IOException 
	 */
	public ErrorCode exit( String name ) throws IOException{
		
//		onlineUsers.remove( user.getName() );
		UserInfo user = onlineUsers.get( name );
		if( user != null ){
		//synchronized ( user ) {
			user.setLastLogoutTime( SystemTimer.currentTimeSecond() );
			user.setConClose();
			return db.update(user);
		}
		return ErrorCode.UNKNOW_ERROR;
	}
	
	
	/**
	 * 创建一个新玩家，玩家名以及其他基本属性由user指定
	 * @param user
	 * @return
	 */
	public synchronized ErrorCode create( UserInfo user ){
		ErrorCode code = db.create( user ) ;
		if( code == ErrorCode.SUCCESS ){
			user.getTaskManager().addFirstTask();
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
		for( Entry<String, UserInfo> e : onlineUsers.entrySet() ){
			sb.append( "[" );
			sb.append( e.getValue() );
			sb.append( "]\n" );
		}
		sb.append( "]" );
		return sb.toString();
	}
	/**
	 * 通过用户名从数据库获取玩家信息,不管是否在线，只要该玩家确实在数据库中存在，就尽力保存到内存当中来，是否在线无所谓
	 * @param string
	 * @return
	 * 
	 * 			如果不存在则返回null
	 * 
	 */
	public UserInfo getUserByName(String name) {
		if( name == null ){
			return null;
		}
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
	 * 通过昵称获取玩家信息
	 * @param string
	 * @return
	 * 
	 */
	public UserInfo getUserByNickName( String nickName ) {
		String name = db.getNameByNickName( nickName );
		if( name == null ){
			return null;
		}
		return getUserByName(name);
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
	public ErrorCode packageRun( String name, EventManager pack, byte[] data ) throws IOException {

		UserInfo user = getUserByName( name );
		if( user == null ){
			return ErrorCode.USER_NOT_FOUND;
		}
		if( user.getPackageManager().safeCheck( pack ) == false ){
			return ErrorCode.PACKAGE_SAFE_CHECK_FAIL;
		}
		ByteBuffer buf = ByteBuffer.wrap( data );
		pack.run( user, buf );
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
	 * 
	 * 外层保证玩家不会重复登陆（重复发送登陆包）
	 */
	public ErrorCode login( INonBlockingConnection con, ByteBuffer buf ) throws IOException {
		
		String name = util.BaseUtil.decodeString( buf );

		UserInfo user = getUserByName( name );
		if( user == null ){
			return ErrorCode.USER_NOT_FOUND;
		}
		if( user.getStatus() == UserStatus.BAN ){
			return ErrorCode.USER_HAS_BAN;
		}
		return user.setConLogin( con, name );
	}
	
	public static void main(String[] args) {
		ConcurrentMap<String, Integer > map = new ConcurrentHashMap<String, Integer>();
		System.out.println( map.putIfAbsent( "a", 2));
		System.out.println( map.putIfAbsent( "a", 3));
		System.out.println( map.putIfAbsent( "a", 5));
		for( Entry<String, Integer> e : map.entrySet() ){
			System.out.println( e.getKey() + "=" + e.getValue() );
		}
		
		int count = 1000000;
		ConcurrentHashMap<String, UserInfo> users = new ConcurrentHashMap<String, UserInfo>();
		long begin = System.nanoTime();
		for( int i = 0; i < count; i++ ){
			String name = "bbz"+i;
			UserInfo u = new UserInfo(null, name );
			users.put( name, u );
		}
		System.out.println( "写入" + count +"个haspmap的entry用时" + (System.nanoTime() - begin) / 1000000000f + "秒" );
		
		java.util.Random r = new Random();
		begin = System.nanoTime();
		for( int i = 0; i < 10000; i++ ){
			String name = "bbz" + r.nextInt( 10000 );
			UserInfo u = users.get( name );
			if( u != null ){
				//System.out.print( u.getName() + ",");
			}
		}
		System.out.println();
		System.out.println( "随机10000次get一个拥有" + count +"个元素的hashMap用时" + (System.nanoTime() - begin) / 1000000000f + "秒" );
		
		begin = System.nanoTime();
		
		for( int i = 0; i < count; i++ ){
			String name = "bbz"+i;
			UserInfo u = users.get(name);
			if( u != null ){
				//System.out.print( u.getName() + "," );
				if( u.isOnline() ){
					System.out.println( "3");
				}
			}
		}
		System.out.println( "for循环遍历拥有" + count +"个元素的hashMap用时" + (System.nanoTime() - begin) / 1000000000f + "秒" );
		
		begin = System.nanoTime();
		
		for( Entry<String, UserInfo> e : users.entrySet() ){
			UserInfo u = e.getValue();
			if( u.isOnline() ){
				System.out.println( "3");
			}
			
		}
		System.out.println( "for-each循环遍历拥有" + count +"个元素的hashMap用时" + (System.nanoTime() - begin) / 1000000000f + "秒" );
		
		begin = System.nanoTime();
		String nickName = "巴比猪1";//真实存在的昵称
		for( int i = 0; i < 1000; i++ ){
			UserManager.getInstance().getUserByNickName(nickName);
		}
		System.out.println("用时" + (System.nanoTime() - begin) / 1000000000f + "秒");
	}

}
