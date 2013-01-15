package game.events;


import game.events.all.*;
import game.events.all.task.TaskAcceptAwardEvent;
import game.events.all.task.TaskAcceptEvent;
import game.events.all.task.TaskGetAllActiveEvent;
import game.events.all.task.TaskGetEvent;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import user.UserInfo;

/**
 * 100以内为系统保留
 * 200以内为系统功能需求
 * 500以上为游戏需要
 * 
 * 30000以上为测试用(暂时)
 * 
 * @注意：为了传输方便，枚举对应的数字也就是包号不得超过Short.MAX_VALUE
 * @author liukun
 * 2012-8-25
 */
public enum EventManager {
	

	SYSTEM_SEND_ERROR_CODE				( 100, 		new SystemSendErrorCodeEvent() ),	
	/*********************************系统保留******************************************/
	
	USER_LOGIN							( 201, 		new UserLoginEvent() ),
	USER_CREATE							( 202, 		new UserCreateEvent() ),
	USER_EXIT							( 203, 		new UserExitEvent() ),	
	/*********************************用户系统******************************************/
	
	EQUIPMENT_LEVEL_UP					( 501, 		new EquipmentLevelUpEvent() ),	
	/*********************************装备系统******************************************/
	
	TASK_GET_ALL_ACTIVE					( 701, 		new TaskGetAllActiveEvent() ),
	TASK_ACCEPT_AWARD					( 702, 		new TaskAcceptAwardEvent() ),
	TASK_ACCEPT							( 703, 		new TaskAcceptEvent() ),
	TASK_GET							( 704, 		new TaskGetEvent() ),
	/*********************************任务系统******************************************/
	
	/*********************************背包系统******************************************/
	
	/*********************************测试系统******************************************/
	DEAD_LOCK_TEST						( 30000, 	new DeadLockTestEvent() ); 
		
	private final short 			number;
	private final BaseEvent 		eventInstance;
	
	EventManager( int value, BaseEvent eventInstance ) {
		if( value >= Short.MAX_VALUE || value < 0 ){
			throw new IllegalArgumentException( "包号不符合规范：" + value );
		}
		this.number =  (short) value;
		this.eventInstance = eventInstance;
		this.eventInstance.setPackageNo( number );
	}
	private static final Map<Short, EventManager> numToEnum = new HashMap<Short, EventManager>();
	
	static{
		for( EventManager a : values() ){
			
			EventManager p = numToEnum.put( a.number, a );
			if( p != null ){
				throw new RuntimeException( "通信包" + a.number + "重复了" );
			}
		}
	}
	
	public BaseEvent getEventInstance() {
		return eventInstance;
	}
	public short toNum() {
		return number;
	}
	public static EventManager fromNum( short n ){
		return numToEnum.get( n );
	}
	
	/**
	 * 运行此枚举所对应的包的run方法
	 * @param user
	 * @param buf
	 * @throws IOException 
	 */
	public void run( UserInfo user, ByteBuffer buf ) throws IOException {
		eventInstance.run( user, buf );
	}
	
	/**
	 * 打印所有的包信息
	 * @param args
	 */
	public static void main(String[] args) {
		Formatter f = new Formatter( System.out );
		f.format( "%-15s %-100s %-10s \n", "包号", "类别", "功能说明" );
		f.format( "%-15s %-100s %-10s \n", "－－", "－－", "－－－－" );
		for( EventManager p : values() ){
			
			Class<?> c = p.eventInstance.getClass();
			EventDescrip desc = c.getAnnotation(EventDescrip.class);
			String s = null;
			s = (desc == null) ? "" : desc.desc();
			String className = c.getName().substring( c.getName().lastIndexOf(".") + 1 );
			f.format("%-8s %-40s %-10s \n", p.eventInstance.getEventId(), className, s );
		}
		System.out.println( "--------------------------HTML---------------------------------");
		StringBuilder html = new StringBuilder( "<table><tr><td>包号</td><td>类别</td><td>功能说明</td></tr><tr>" );
		for( EventManager p : values() ){
			
			Class<?> c = p.eventInstance.getClass();
			EventDescrip desc = c.getAnnotation(EventDescrip.class);
			String s = null;
			s = (desc == null) ? "" : desc.desc();
			String className = c.getName().substring( c.getName().lastIndexOf(".") + 1 );
		//	f.format("%-8s %-40s %-10s \n", p.packageInstance.getPackageNo(), className, s );
			html.append( "<td>" );
			html.append( p.eventInstance.getEventId() );
			html.append( "</td><td>" );
			html.append( className );
			html.append( "</td><td>" );
			html.append( s );
			html.append( "</td></tr>" );
			
			
		} 
		html.append(  "</table>" );
		System.out.println( html );
		f.close();
	}
	
}
