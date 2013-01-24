package user;

import game.award.AwardInfo;
import game.award.AwardType;
import game.task.TaskManager;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xsocket.connection.INonBlockingConnection;

import util.BaseUtil;
import util.ErrorCode;

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
	private final UserPackageManager					packageManager;
	
	/**
	 * 任务管理器
	 */
	private TaskManager									taskManager;
	
	/**
	 * 底层的网络连接，
	 */
	private 	INonBlockingConnection	 				con;
	
	/**
	 * 用户名
	 */
	private final String								name;
	
	/**
	 * 当前玩家的状态
	 */
	private UserStatus 									status = UserStatus.GUEST;

	/**
	 * 现金
	 */
	private int											cash;
	
	/**
	 * 金币，点券
	 */
	private int											gold;
	
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
	 * @param sex 
	 * @param nickName 
	 */
	public UserInfo( INonBlockingConnection con, String name, String nickName, byte sex ) {
		this.con = con;
		this.packageManager = new UserPackageManager();
		this.name = name;
		this.nickName = nickName;
		this.sex = sex;
	}
	
	public UserInfo( INonBlockingConnection con, String name ) {
		this.con = con;
		this.packageManager = new UserPackageManager();
		this.name = name;
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
	* @return 					< 0		体力扣除失败
	 * 							>=0		当前拥有的体力
	 */	
	synchronized int changeStrength( int change, String funcName ){
		if( strength + change < 0 ){
			logger.debug( name + "拥有体力：" + strength + "欲扣除体力：" + change + "，出现负数，调用函数为" + funcName );
			return -1;
		}
		strength += change;
		buildLog( AwardType.STRENGTH, change, strength, funcName );
		return strength;
	}

	synchronized public TaskManager getTaskManager(){
		if( taskManager == null ){
			taskManager = new TaskManager( this );
		}
		return taskManager;
	}
	
	
	
	public int getCash(  ){
		return cash;
	}
	/**
	 * 修改玩家的金币数量
	 * @param change			增加为正数，减少为负数
	 * @param funcName			调用的函数
	 * 
	 * @return 					-1		扣除失败<br>
	 * 							>=0		当前拥有的现金<br>
	 * 
	 * 注意：仅仅用于从数据库中进行初始化操作，其余的修改请调用{@link #changeAward(AwardType, int, String)}
	 */
	public int setCash( int change ){
		
		if( cash + change < 0 ){
			
			return -1;
		}
		cash += change;
		
		//TODO 处理防沉迷系统，其他的vip加成等信息
		
//		Thread thr = Thread.currentThread();
//      StackTraceElement[] ele = thr.getStackTrace();
//      String func = ele[2].getMethodName();
//      System.out.println(func);

//		buildLog( AwardType.CASH, change, cash, funcName );
		return cash;
	}
	
	
	/**
	 * 获取玩家金币
	 * @return
	 */
	public synchronized int getGold(  ){
		return gold;
	}
	
	/**
	 * 修改玩家的金币数量
	 * @param change			增加为正数，减少为负数
	 * @param funcName			调用的函数
	 * 
	 * @return 					< 0		扣除失败<br>
	 * 							>=0		当前拥有的金币<br>
	 * 
	 * 	注意：仅仅用于从数据库中进行初始化操作，其余的修改请调用{@link #changeAward(AwardType, int, String)}
	 */
	public int setGold( int change ){
		
		if( gold + change < 0 ){
			return -1;
		}
		gold += change;
		
		//TODO 处理防沉迷系统，其他的vip加成等信息
		
		return cash;
	}
	
	/**
	 * 玩家涉及到属性的更改操作
	 * @param type
	 * @param number
	 * @param funcName
	 * @return
	 * 		>=0	:返回此属性的当前值
	 * 		-1	:扣除失败，余值不足
	 * 		
	 */
	public int changeAward( AwardType type, int number, String funcName ){
		int result = 0;
		switch( type ){
		case CASH:
			result = this.setCash( number );
			break;
		default:
			throw new IllegalArgumentException( type + "属性不存在相应函数" );
		}
		if( result == -1 ){
			logger.debug( name + "奖励类型：" + type + "欲改变数值：" + number + "，出现负数，调用函数为" + funcName );
		}
		buildLog( type, number, result, funcName );
		return result;
	}
	
	public void getAward( AwardInfo award, String funcName ){
		int result = 0;
		switch( award.getAward() ){
		case CASH:
			result = this.setCash( award.getNumber() );
		default:
			break;
		}
		if( result == -1 ){
			logger.debug( name + "奖励类型：" + award.getAward() + "欲改变数值：" + award.getNumber() + "，出现负数，调用函数为" + funcName );
		}
		buildLog( award.getAward(), award.getNumber(), result, funcName );
	}
	
	/**
	 * 构造关键数据的日志文件
	 * @param at			奖励类型
	 * @param change		改动的数值
	 * @param current		改动后的当前数值
	 * @param funcName		改动函数
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
	
	public synchronized INonBlockingConnection getCon () {
		return con;
	}
	
	/**
	 * 设置登录连接，方案如下：
	 * 如果原本无con连接，直接赋值，并设置con的Attachment
	 * 如果原本有连接，则主动切断原有连接并返回USER_HAS_LOGIN标识，让客户端等待500ms后重试
	 * 
	 * con.setAttachment( name );这句代码可能造成死锁，但是好像也不能移到user锁之外，因为必须保证user的con为此con，而此con的attachment必须为user的name<br>
	 * 保证不会在对con进行大规模加锁的封闭调用，则可以避免
	 * 应该不会行成死锁，因为setAttachment函数仅仅在此处调用2012-10-10
	 * 这属于不变条件
	 * 
	 * @param con
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public synchronized ErrorCode setConLogin ( INonBlockingConnection con, String name ) throws IOException {
		ErrorCode code = ErrorCode.SUCCESS;
		if( this.con != null ){
			this.con.close();
			//this.con = null;
			code = ErrorCode.USER_HAS_LOGIN;
		}
		else{
			this.con = con;
			con.setAttachment( name );
			
		}
		return code;
	}
	
	/**
	 *  这里无需再次调用close函数，因为关闭连接无非
	 * 	1、客户端主动发起，这个时候，连接已经关闭
	 * 	2、服务器主动发起，在发起处，已经调用过close了
	 * 
	 * 	只需把con置为null即可
	 * @throws IOException
	 */
	public synchronized void setConClose() throws IOException{
		con = null;
	}
	public UserPackageManager getPackageManager () {
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
	
	/**
	 * 运行包处理程序，多线程下，唯一的风险是getStatus()会不会在这里为UserStatus.LOGIN之后，被另外的线程修改为其他状态，导致竞态条件的产生<br>
	 * 再想想
	 * 为什么不直接加锁解决呢？2012-10-10
	 * 此函数已废弃2012-10-10
	 
	public ErrorCode run( Packages pack, byte[] data ){
		
		if( !packageManager.safeCheck( pack ) ){
			return ErrorCode.PACKAGE_SAFE_CHECK_FAIL;
		}
		if( (pack == Packages.USER_LOGIN || pack == Packages.USER_CREATE) && getStatus() == UserStatus.LOGIN ) {
			return ErrorCode.USER_HAS_LOGIN;
		}
		else if( (pack != Packages.USER_LOGIN && pack != Packages.USER_CREATE) && getStatus() == UserStatus.GUEST ) {
			return ErrorCode.USER_NOT_LOGIN;
		}
		
		ByteBuffer buf = ByteBuffer.wrap( data );
		try{
			pack.run( this, buf );
		}
		catch( Exception e ){
			logger.debug( this + "," + pack + "," + BaseUtil.bufToString( buf ), e );
			return ErrorCode.UNKNOW_ERROR;
		}
		return ErrorCode.SUCCESS;
				
	}*/
	@Override
	public synchronized String toString() {
		
		String connStr = con == null ? "null" : con.getId();
		return "UserInfo[name=" + name + ", conn=" + connStr 
				+ ", status=" + status + ", money=" + cash + ", strength="
				+ strength + ", nickName=" + nickName
				+ ", level=" + level + ", createTime=" + BaseUtil.secondsToDateStr( createTime )
				+ ", lastLogoutTime=" + BaseUtil.secondsToDateStr( lastLogoutTime ) + ", loginCount="
				+ loginCount + ", sex=" + sex + ", isAdult=" + isAdult + "]";
	}

	/**
	 * 玩家是否在线
	 * @return
	 */
	public synchronized boolean isOnline(){
		return (con != null && con.isOpen());
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
