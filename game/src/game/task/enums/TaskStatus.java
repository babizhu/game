package game.task.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * 任务的各种状态
 * @author liukun
 *
 */
public enum TaskStatus {
	/**
	 * 可接任务状态，尚未接状态
	 */
	CAN_ACCEPT(1),
	
	/**
	* 接受任务，尚未完成的状态
	*/
	ACCEPT(2),	
	
	/**
	 * 完成任务，尚未领奖的状态
	 */
	NO_REWARD(3),
	
	/**
	* 领奖，任务彻底完成状态
	*/
	FINISH(4);
	
	private byte number;
	
	private static final Map<Byte, TaskStatus> numToEnum = new HashMap<Byte, TaskStatus>();
	static{
		for( TaskStatus t : values() ){
			
			TaskStatus s = numToEnum.put( t.number, t );
			if( s != null ){
				throw new RuntimeException( t.number + "重复了" );
			}
		}
	}
	
	TaskStatus( int number ){
		this.number = (byte) number;
	}
	
	public short toNum() {
		return number;
	}
	public static TaskStatus fromNum( byte n ){
		return numToEnum.get( n );
	}

}
