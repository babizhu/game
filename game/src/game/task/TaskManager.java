package game.task;

import game.task.enums.TaskStatus;
import game.task.enums.TaskType;

import java.util.ArrayList;
import java.util.List;

import util.ErrorCode;


public class TaskManager {

//	private Map<Integer,BaseTask> task = new HashMap<Integer, BaseTask>();
	private List<BaseTask> tasks = new ArrayList<BaseTask>();


	/**
	 * 完成一次任务
	 * @param type
	 * @param obj
	 * @return
	 */
	ErrorCode doTask( TaskType type, Object obj ){
		for( BaseTask t : tasks ){
			if( t.getStatus() == TaskStatus.ACCEPT && t.getTaskType() == type ){
				t.doTask( obj );
				if( t.getStatus() == TaskStatus.FINISH ){
					finishTask( t );
				}
				break;//假设同类任务只有一个，这属于一个优化，一旦发现问题，马上去掉
			}
		}
		return null;
	}

	
	/**
	 * 某个任务完成之后的后续工作，例如，打开新的可接任务，发送信息到客户端，etc
	 * @param task
	 */
	private void finishTask( BaseTask task ){
		
	}
	
	
	
	

}
