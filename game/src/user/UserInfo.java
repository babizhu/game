package user;

import game.ObjectType;

import java.nio.ByteBuffer;

import org.slf4j.Logger; 

import org.slf4j.LoggerFactory; 



//import org.slf4j.; 



public class UserInfo {
	private final static Logger logger = LoggerFactory.getLogger( UserInfo.class ); 


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
		buildLog( ObjectType.STRENGTH, strengthAdd, strength, funcName );
		return strength;
	}
	public int reduceStrength( int strengthReduce, String funcName ){
		if( strength < strengthReduce ){
			throw new IllegalArgumentException();
		}
		strength -= strengthReduce;
		buildLog( ObjectType.STRENGTH, -strengthReduce, strength, funcName );
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

		buildLog( ObjectType.MONEY, moneyAdd, money, funcName );
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
		buildLog( ObjectType.MONEY, -moneyReduce, money, funcName );
		return money;
	}
	
	
	public void sendPacket( ByteBuffer[] buffer ){
		
	}
	
	/**
	 * 构造关键数据的日志文件
	 * @param at
	 * @param change
	 * @param current
	 * @param funcName
	 */
	private void buildLog( ObjectType at, int change, int current, String funcName ){
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
