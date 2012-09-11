package user;

import game.AwardType;
import game.packages.PackageManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;

import util.BaseUtil;

/**
 * 用户基础信息类
 * 
 * 线程安全
 * 
 * @author liukun
 * 2012-9-11 下午02:24:20
 */
public class UserInfo {
	private final static Logger logger = LoggerFactory.getLogger( UserInfo.class ); 

	/**
	 * 包管理器
	 */
	private final PackageManager						packageManager;
	
	/**
	 * 底层的网络连接，
	 */
	private final INonBlockingConnection 				conn;
	
	/**
	 * 用户名
	 */
	private final String								name;
	
	/**
	 * 当前玩家的状态
	 */
	private UserStatus 									status = UserStatus.GUEST;

	/**
	 * 金币
	 */
	private int											money;
	
	/**
	 * 体力
	 */
	private short										strength = 0;
	
	/**
	 * 昵称
	 */
	private String										nickName;
	
	/**
	 * 等级
	 */
	private short										level;
	
	/**
	 * 创建时间，单位：秒	
	 */
	private int											createTime;
	
	/**
	 * 上次下线时间，单位：秒
	 */
	private int											lastLogoutTime;
	
	/**
	 * 玩家总的登陆次数
	 */
	private short										loginCount;
	
	private byte										sex						= 1;
	
	private boolean										isAdult					= true;
	

	
	/**
	 * 构造函数，保持一个尽量精简的构造函数
	 */
	public UserInfo( INonBlockingConnection conn, String name ) {
		this.conn = conn;
		this.name = name;
		this.packageManager = new PackageManager();
	}
	
	public synchronized short getLevel () {
		return level;
	}
	public synchronized void setLevel ( short level ) {
		this.level = level;
	}
	/**
	 * 修改体力
	 * @param change		增加为正数，减少为负数
	 * @param funcName
	 * @return				当前体力值
	 */	
	synchronized int changeStrength( int change, String funcName ){
		strength += change;
		buildLog( AwardType.STRENGTH, change, strength, funcName );
		return strength;
	}

	
	public synchronized int getMoney(  ){
		return money;
	}
	/**
	 * 修改玩家的金币数量
	 * @param change			增加为正数，减少为负数
	 * @param funcName			调用的函数
	 * 
	 * @return 					当前拥有的金币
	 */
	public synchronized int changeMoney( int change, String funcName ){
		money += change;
		
		//TODO 处理防沉迷系统，其他的vip加成等信息
		
//		Thread thr = Thread.currentThread();
//      StackTraceElement[] ele = thr.getStackTrace();
//      String func = ele[2].getMethodName();
//      System.out.println(func);

		buildLog( AwardType.MONEY, change, money, funcName );
		return money;
	}
	
	
	/**
	 * 构造关键数据的日志文件
	 * @param at
	 * @param change
	 * @param current
	 * @param funcName
	 */
	private void buildLog( AwardType at, int change, int current, String funcName ){
		
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
	
	public synchronized String getNickName () {
		return nickName;
	}
	
	public synchronized void setNickName ( String nickName ) {
		this.nickName = nickName;
	}
	public String getName () {
		return name;
	}

	public synchronized UserStatus getStatus () {
		return status;
	}
	public synchronized void setStatus ( UserStatus status ) {
		this.status = status;
	}
	
	public INonBlockingConnection getConn () {
		return conn;
	}
	
	public PackageManager getPackageManager () {
		return packageManager;
	}
	
	
	public synchronized int getCreateTime() {
		return createTime;
	}

	public synchronized void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public synchronized int getLastLogoutTime() {
		return lastLogoutTime;
	}

	synchronized void setLastLogoutTime(int lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}

	public synchronized short getLoginCount() {
		return loginCount;
	}

	public synchronized void setLoginCount(short loginCount) {
		this.loginCount = loginCount;
	}

	public synchronized byte getSex() {
		return sex;
	}

	public synchronized void setSex(byte sex) {
		this.sex = sex;
	}

	public synchronized boolean isAdult() {
		return isAdult;
	}

	public synchronized void setAdult(boolean isAdult) {
		this.isAdult = isAdult;
	}

	
	public synchronized short getStrength() {
		return strength;
	}

	public synchronized void setStrength(short strength) {
		this.strength = strength;
	}


//	/**
//	 * 拷贝构造函数，除开以下变量，其余都要复制：<br>
//	 * this.conn;
//	 * this.name
//	 * @param user
//	 */
//	public void copy( UserInfo user ){
//		
//		this.createTime = user.createTime;
//		this.isAdult = user.isAdult;
//		this.lastLogoutTime = user.lastLogoutTime;
//		this.level = user.level;
//		this.loginCount = user.loginCount;
//		this.money = user.money;
//		this.nickName = user.nickName;
//		this.sex = user.sex;
//		this.status = user.status;
//		this.strength = user.strength;
//	
//	}
	
	@Override
	public synchronized String toString() {
		
		String connStr = conn == null ? "null" : conn.getId();
		return "UserInfo[name=" + name + ", conn=" + connStr 
				+ ", status=" + status + ", money=" + money + ", strength="
				+ strength + ", nickName=" + nickName
				+ ", level=" + level + ", createTime=" + BaseUtil.secondsToDateStr( createTime )
				+ ", lastLogoutTime=" + BaseUtil.secondsToDateStr( lastLogoutTime ) + ", loginCount="
				+ loginCount + ", sex=" + sex + ", isAdult=" + isAdult + "]";
	}



public static void main ( String[] args ) {
//	long begin = System.nanoTime();
//	for( int i = 0; i < 100000000; i++ ){
//		UserInfo user = new UserInfo(null, "bbz");
//		user.changeMoney(i, "");
//	}
//	
//	System.out.println("用时" + (System.nanoTime() - begin) / 1000000000f + "秒");
//		
	}
	
	

}
