package game.task.enums;

/**
 * 任务的各种状态
 * @author liukun
 *
 */
public enum TaskStatus {
	/**可接任务状态，尚未接状态
	 * 
	 */
	CAN_ACCEPT,
	/**
	* 接受任务，尚未完成的状态
	*/
	ACCEPT,	
	/**
	 * 完成任务，尚未领奖的状态
	 */
	NO_REWARD,
	
	/**
	* 领奖，任务彻底完成状态
	*/
	FINISH;

}
