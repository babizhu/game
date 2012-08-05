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


	public ErrorCode changeStatus( long taskId, TaskStatus status ){
		BaseTask task = getTaskById( taskId );
		if( task == null ){
			return ErrorCode.TASK_NOT_FOUND;
		}
		task.setStatus( status );
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 完成一次任务
	 * @param type
	 * @param obj		完成任务所需要的参数
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
		return ErrorCode.SUCCESS;
	}

	
	/**
	 * 某个任务完成之后的后续工作，例如，打开新的可接任务，发送信息到客户端，etc
	 * @param task
	 */
	private void finishTask( BaseTask task ){
		BaseTaskTemplet templet = task.getTemplet();
		
		if( task.getStatus() == TaskStatus.FINISH ){
			addNewTask( templet );
		}
		
		//TODO通知客户端计数类任务+1
	}
	
	/**
	 * 添加可接任务
	 * @param templet
	 */
	private void addNewTask( BaseTaskTemplet templet ){
		if( templet.getOpenTemplet() != null ){
			for( BaseTaskTemplet t : templet.getOpenTemplet() ){
				this.tasks.add( t.createTask() );
				//TODO 写入数据库
				//TODO 通知客户端
			}
		}
	}
	private BaseTask getTaskById(long taskId) {
		for( BaseTask task : tasks ){
			if( task.getId() == taskId ){
				return task;
			}
		}
		return null;
	}
}
