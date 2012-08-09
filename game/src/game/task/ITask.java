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
	
	/**
	 * 还原数据库保存的任务的额外参数信息<br>
	 * 例如 对于收集打怪任务，用于保存当前已经打了多少次怪<br>
	 * 对于扔十次垃圾，保留当前已经扔了多少次垃圾<br>
	 * @param str
	 */
	void parseParam( String str );
	
	
	/**
	 * 构建任务的额外需要保存的信息为一个字符串，格式:parm1,parm2,parm3<br>
	 * 方便保存到数据库中<br>
	 * 例如 对于收集打怪任务，用于保存当前已经打了多少次怪<br>
	 * 对于扔十次垃圾，保留当前已经扔了多少次垃圾<br>
	 * @param str
	 */
	String buildParam( );

}
