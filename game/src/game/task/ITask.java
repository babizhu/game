package game.task;

import game.task.enums.TaskType;



public interface ITask {
	/**
	 * 进行一次任务
	 * @param obj		进行任务所需要的参数
	 */
	void doTask( Object obj );

	
	/**
	 * 返回此任务的类型
	 * @return
	 */
	TaskType getTaskType();

}
