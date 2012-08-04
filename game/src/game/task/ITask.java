package game.task;

import user.UserInfo;
import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;



public interface ITask {
	/**
	 * 进行一次任务
	 * @param obj		进行任务所需要的参数
	 * 
	 * @return
	 * 			如输入参数就是此任务，返回true，否则 false
	 */
	boolean doTask( UserInfo user, Object obj );

	
	/**
	 * 返回此任务的类型
	 * @return
	 */
	TaskType getTaskType();
	
	/**
	 * 返回此任务关联的模板
	 * @return
	 */
	BaseTaskTemplet	getTemplet();

}
