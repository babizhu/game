package game.task;

import game.task.enums.TaskStatus;
import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;

import java.util.ArrayList;
import java.util.List;

import user.UserInfo;
import util.ErrorCode;


public class TaskManager {

//	private Map<Integer,BaseTask> task = new HashMap<Integer, BaseTask>();
	private List<BaseTask> 	tasks = new ArrayList<BaseTask>();
	private UserInfo		user;


	/**
	 * 完成一次任务
	 * @param type
	 * @param obj
	 * @return
	 */
	ErrorCode doTask( TaskType type, Object obj ){
		for( BaseTask t : tasks ){
			if( t.getStatus() == TaskStatus.ACCEPT && t.getTaskType() == type ){
				
				if( t.doTask( user, obj ) ){
					finishTask( t );
					if( type != TaskType.DIRECT ){//假设同类任务只有一个，直接跳出循环（直接完成类的任务例外）。这属于一个优化，一旦发现问题，马上去掉
						break;
					}
				}
				
				
			}
		}
		return null;
	}

	
	/**
	 * 某个任务完成之后的后续工作，例如，打开新的可接任务，发送信息到客户端，etc
	 * @param task
	 */
	private void finishTask( BaseTask task ){
		BaseTaskTemplet templet = task.getTemplet();
		
		if( task.getStatus() == TaskStatus.FINISH ){
			//开启下一级任务，放到玩家的可领任务中
			if( templet.getOpenTempletId() != null ){
				for( BaseTaskTemplet t : templet.getOpenTempletId() ){
					addTask( t );
				}
			}
		}
	}
	
	/**
	 * 添加可接任务
	 * @param t
	 */
	private void addTask( BaseTaskTemplet t ){
		this.tasks.add( t.createTask() );
		//TODO 写入数据库
		//TODO 通知客户端
	}
	
	
	
	

}
