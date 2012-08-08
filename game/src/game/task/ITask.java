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
	 * 		true:	命中任务
	 * 		flase:	未命中任务
	 * <br>
	 * @注意：如果该任务模板的checkNow属性true，一定要记住考虑@obj为null的情况
	 */
	boolean doTask( UserInfo user, Object obj );

//	/**
//	 * 有些任务，在接任务后需要立即进行检测任务是否完成
//	 * 例如道具收集类
//	 * @param user
//	 */
//	void doTaskNow ( UserInfo user );
	
	
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
