package game.task;

import game.task.templet.BaseTaskTemplet;
import user.UserInfo;
import util.ErrorCode;

public interface ITask {
	/**
	 * 进行一次任务
	 * @param obj		进行任务所需要的参数
	 * 
	 * @return
	 * 		true:		命中任务,无论是否完成<br>
	 * 		flase:		未命中任务
	 * 
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
	 * 接任务，每个任务可能有自己 不同的条件， 
	 * @param user TODO
	 * @return
	 */
	ErrorCode acceptTask(UserInfo user );
	
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
	void parseParamFromStr( String str ); 
	
	
	/**
	 * 把此任务所需要的内部参数以Object方式返回，有利于：<br>
	 * 1、客户端显示<br>
	 * 2、保存到数据库，由于数据库的特性，通常这里需要Objce对象提供相应的toString函数<br>
	 * 例如 对于收集打怪任务，用于保存当前已经打了多少次怪<br>
	 * 对于扔十次垃圾的任务，保留当前已经扔了多少次垃圾<br>
	 * 
	 * @注意：如果有其他更复杂的数据结构，请自行在内部建立合适的bean类来保存，并在bean类中建立合适toString函数
	 */
	Object getParam( );
}
