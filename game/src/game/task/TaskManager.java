package game.task;

import game.task.enums.TaskStatus;
import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;

import java.util.LinkedList;
import java.util.List;

import user.UserInfo;
import util.ErrorCode;
import util.SystemTimer;

public class TaskManager {

//	private Map<Integer,BaseTask> task = new HashMap<Integer, BaseTask>();
	private List<BaseTask> 	tasks = new LinkedList<BaseTask>();
	private UserInfo		user;


	public TaskManager( UserInfo user ) {
		super();
		this.user = user;
	}
	/**
	 * 从可接任务中，接一个新任务
	 * @param taskId
	 * @return
	 */
	public ErrorCode acceptTask( long taskId ){
		BaseTask task = this.getTaskById( taskId );
		if( task == null ){
			return ErrorCode.TASK_NOT_FOUND;
		}
		if( task.getStatus() != TaskStatus.CAN_ACCEPT ){
			return ErrorCode.UNKNOW_ERROR;
		}
		if( user.getLevel() < task.getTemplet().getNeedLevel() ){
			return ErrorCode.LEVEL_NOT_ENOUGH;
		}
		
		task.setStatus( TaskStatus.ACCEPT );
		
		if( task.getTemplet().isCheckNow() ){
			task.doTask( user, null );
			if( task != null && task.getStatus() == TaskStatus.NO_REWARD ){
				finishTask( task );
			}
		}
		
		//TODO 写入数据库
		//TODO 通知前端
		return ErrorCode.SUCCESS;
		
	}
	
	public ErrorCode AcceptAward( long taskId ){
		BaseTask task = this.getTaskById( taskId );
		if( task == null ){
			return ErrorCode.TASK_NOT_FOUND;
		}
		if( task.getStatus() != TaskStatus.NO_REWARD ){
			return ErrorCode.UNKNOW_ERROR;
		}
		task.setStatus( TaskStatus.FINISH );
		task.setAcceptTime( SystemTimer.currentTimeMillis() );
		
		//TODO 领奖
		//TODO 写入数据库
		//TODO 通知前端
		
		return ErrorCode.SUCCESS;		
	}
	/**
	 * 完成一次任务
	 * @param type
	 * @param obj		完成任务所需要的参数
	 * @return
	 */
	public ErrorCode doTask( TaskType type, Object obj ){
		BaseTask task = null;
		for( BaseTask t : tasks ){
			if( t.getStatus() == TaskStatus.ACCEPT && t.getTaskType() == type ){
				
				if( t.doTask( user, obj ) ){//限制：一次只允许完成一个任务，请策划确保不会出现两个需求相同的任务
					task = t;
					//finishTask( t );
					break;					
				}
			}
		}
		if( task != null && task.getStatus() == TaskStatus.NO_REWARD ){
			finishTask( task );
		}
		return ErrorCode.SUCCESS;
	}

	/**
	 * 添加第一个初始任务
	 */
	public void addFirstTask( BaseTaskTemplet templet ){
		BaseTask task = templet.createTask();
		task.setStatus( TaskStatus.ACCEPT );//第一个任务缺省设置为已接状态
		//TODO 写入数据库，填充id字段
		tasks.add( task );
	}
	
	public List<BaseTask> getTasks(){
		return tasks;
	}
	
	
//	private ErrorCode changeStatus( long taskId, TaskStatus status ){
//		BaseTask task = getTaskById( taskId );
//		if( task == null ){
//			return ErrorCode.TASK_NOT_FOUND;
//		}
//		task.setStatus( status );
//		return ErrorCode.SUCCESS;
//	}
	
	/**
	 * 某个任务完成之后的后续工作，例如发送信息到客户端，etc
	 * @param task
	 */
	private void finishTask( BaseTask task ){
		BaseTaskTemplet templet = task.getTemplet();
		addSuccessorTask( templet );
	}
	
	/**
	 * 完成一个任务之后，添加此任务的后继可接任务
	 * @param templet
	 * 
	 */
	private void addSuccessorTask( BaseTaskTemplet templet ){
		
		if( templet.getSuccessorTemplet() != null ){
			for( BaseTaskTemplet t : templet.getSuccessorTemplet() ){
				tasks.add( t.createTask() );
				//TODO 写入数据库
				//TODO 通知客户端
			}
		}
	}
	
	
	private BaseTask getTaskById( long taskId ) {
		for( BaseTask task : tasks ){
			if( task.getId() == taskId ){
				return task;
			}
		}
		return null;
	}
	
	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder( "user=" + user.getName() );
		sb.append( ", task=[" );
		for( BaseTask t : tasks ){
			sb.append( "[id=" + t.getTemplet().getTempletId() );
			sb.append( ", name=" + t.getTemplet().getName() );
			sb.append( ",status=" + t.getStatus() );
			sb.append( "]" );
		}
		
		sb.append( "]" );
		return sb.toString();
	}		
}
