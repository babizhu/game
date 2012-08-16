package user;

import game.AwardType;
import game.packages.PackageManager;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;



//import org.slf4j.; 



public class UserInfo {
	private final static Logger logger = LoggerFactory.getLogger( UserInfo.class ); 

	/**
	 * 包管理器
	 */
	PackageManager			packageManager = new PackageManager();
	/**
	 * 底层的网络连接
	 */
	INonBlockingConnection conn;
	
	/**
	 * 当前玩家的状态
	 */
	private UserStatus status = UserStatus.GUEST;

	/**
	 * 金币
	 */
	int			money;
	
	/**
	 * 体力
	 */
	int			strength;
	
	/**
	 * 昵称
	 */
	String		nickName;
	
	/**
	 * 用户名
	 */
	String		name;
	
	/**
	 * 等级
	 */
	byte		level;
	
	/**
	 * 缺省的空白构造函数，用于测试函数等地方使用，以后如不需要，可删除
	 * @param conn
	 */
	public UserInfo() {

	}
	
	/**
	 * 构造函数，保持一个尽量精简的构造函数
	 */
	public UserInfo( INonBlockingConnection conn ) {
		this.conn = conn;
	}
	
	public byte getLevel () {
		return level;
	}
	public void setLevel ( byte level ) {
		this.level = level;
	}
	/**
	 * 增加体力
	 * @param strengthAdd
	 * @param funcName
	 * @return
	 */	
	public int addStrength( int strengthAdd, String funcName ){
		strength += strengthAdd;
		buildLog( AwardType.STRENGTH, strengthAdd, strength, funcName );
		return strength;
	}
	public int reduceStrength( int strengthReduce, String funcName ){
		if( strength < strengthReduce ){
			throw new IllegalArgumentException();
		}
		strength -= strengthReduce;
		buildLog( AwardType.STRENGTH, -strengthReduce, strength, funcName );
		return strength;
	}
	
	/**
	 * 增加金币
	 * @param add		增加的金币
	 * @param funcName	调用的函数
	 * 
	 * @return 当前所有的金币
	 */
	public int addMoney( int moneyAdd, String funcName ){
		money += moneyAdd;
		
		//TODO 处理防沉迷系统
		
//		Thread thr = Thread.currentThread();
//      StackTraceElement[] ele = thr.getStackTrace();
//      String func = ele[2].getMethodName();
//      System.out.println(func);

		buildLog( AwardType.MONEY, moneyAdd, money, funcName );
		return money;
	}
	
	/**
	 * 扣除金币
	 * @param moneyReduce	扣除的金币
	 * 
	 * @return 当前金币
	 */
	public int reduceMoney( int moneyReduce, String funcName ){
		if( money < moneyReduce ){
			throw new IllegalArgumentException();
		}
		money -= moneyReduce;
		buildLog( AwardType.MONEY, -moneyReduce, money, funcName );
		return money;
	}
	
	
	public void sendPacket( ByteBuffer[] buffer ){
		
		ByteBuffer header = buffer[0];
		
		System.out.println( header );
		System.out.println( "包头" + header.get() );
		System.out.println( "包号:" + header.getShort() );
		System.out.println( "包长1:" + header.getShort() );
		
		ByteBuffer footer = buffer[2];
		System.out.println( "包尾:" + footer.get() );
		try {
			conn.write( buffer );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造关键数据的日志文件
	 * @param at
	 * @param change
	 * @param current
	 * @param funcName
	 */
	private void buildLog( AwardType at, int change, int current, String funcName ){
//		String log = at.toString();
//		log += ",";
//		log += change;
//		log += ",";
//		log += current;
//		log += ",";
//		log += funcName;
		
		StringBuilder sb = new StringBuilder();
		sb.append( name );		//用户名
		sb.append( "," );
		sb.append( at );		//物品类型
		sb.append( "," );
		sb.append( change );	//变化值
		sb.append( "," );
		sb.append( current );	//当前值
		sb.append( "," );
		sb.append( funcName );	//调用的方法名
		
		
		sb.toString();
		
		logger.info( sb.toString() );
	}
	
	public String getNickName () {
		return nickName;
	}
	public void setNickName ( String nickName ) {
		this.nickName = nickName;
	}
	public String getName () {
		return name;
	}
	public void setName ( String name ) {
		this.name = name;
	}
	

	public UserStatus getStatus () {
		return status;
	}
	public void setStatus ( UserStatus status ) {
		this.status = status;
	}
	
	
	public INonBlockingConnection getConn () {
		return conn;
	}
	
	
	public PackageManager getPackageManager () {
		return packageManager;
	}
	public void setPackageManager ( PackageManager packageManager ) {
		this.packageManager = packageManager;
	}
	public static void main ( String[] args ) {
		
		for( int i = 0; i < 10000; i++ ){
		UserInfo user = new UserInfo();
		user.addMoney( 50, "main" );
		
		user.reduceMoney( 20, "main" );
		
		user.addMoney( 20, "main" );
		user.reduceMoney( 20, "main" );
		
		user.addStrength( 500, "练功" );
		user.reduceStrength( 200, "扫荡" );
		}
	}

}
