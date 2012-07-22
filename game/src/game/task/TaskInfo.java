package game.task;

/**
 * 保存玩家的任务bean信息
 * @author liukun
 *
 */
public class TaskInfo {
	/**
	 * 所接任务的状态
	 */
	private TaskStatus 		status;
	
	/**
	 * 唯一id
	 */
	private long			id;
	
	/**
	 *  关联的任务模版id
	 */
	private TaskTemplet		templet;
	
	/**
	 * 如果是统计任务，这里记录当前完成的数字
	 */
	private	int				count;
	

}
