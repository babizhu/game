package game.task;

import game.task.cfg.TaskTempletCfg;
import game.task.enums.TaskStatus;
import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import user.UserInfo;
import util.ErrorCode;
import util.SystemTimer;

/**
 * 玩家任务管理器
 * @author liukun
 * 2012-9-21 下午05:45:18
 * 
 * 同步的思考：<br>
<<<<<<< HEAD
=======
 * 
>>>>>>> branch 'master' of https://github.com/babizhu/game.git
 */
public class TaskManager {

	/**
	 * 为什么要用map，不用list，请参考 
	 * {@link test.PerfomanceTest}.
	 * @param		Short				为任务的模板id
	 * @param		BaseTask			所接任务
	 */
	private Map<Short,BaseTask> tasks = new HashMap<Short, BaseTask>();
//	private List<BaseTask> 	tasks = new LinkedList<BaseTask>();
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
	public ErrorCode acceptTask( short templetId ){
		BaseTask task = this.getTaskCopyByTempletId( templetId );
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
		task.setAcceptTime( SystemTimer.currentTimeMillis() );
		
		if( task.getTemplet().isCheckNow() ){
			doTask( task, null );
		}
		
		//TODO 写入数据库
		//TODO 通知前端
		return ErrorCode.SUCCESS;
	}
	
	public ErrorCode acceptAward( short templetId ){
		BaseTask task = this.getTaskCopyByTempletId( templetId );
		if( task == null ){
			return ErrorCode.TASK_NOT_FOUND;
		}
		if( task.getStatus() != TaskStatus.NO_REWARD ){
			return ErrorCode.UNKNOW_ERROR;
		}
		task.setStatus( TaskStatus.FINISH );
		task.setAcceptAwardTime( SystemTimer.currentTimeMillis() );
		
		tasks.remove( templetId );
		
		//TODO 玩家领奖
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
		for( Entry<Short,BaseTask> e : tasks.entrySet() ){
			BaseTask t = e.getValue();
			if( t.getStatus() == TaskStatus.ACCEPT && t.getTemplet().getTaskType() == type ){
				
				if( doTask( t, obj ) ){//限制：一次只允许完成一个任务，请策划确保不会出现两个需求相同的任务
					return ErrorCode.SUCCESS;					
				}
			}
		}
		return ErrorCode.TASK_NOT_FOUND;
	}

	/**
	 * 添加第一个初始任务
	 * 
	 * @param		任务模板
	 */
	public void addFirstTask( BaseTaskTemplet templet ){
		BaseTask task = templet.createTask();
		task.setStatus( TaskStatus.ACCEPT );//第一个任务缺省设置为已接状态
		//TODO 写入数据库，填充id字段
		tasks.put( templet.getTempletId(), task );
	}
	
	/**
	 * 此方法应该为私有，但为了test方便，暂时用缺省的安全机制
	 * @return
	 */
	Map<Short,BaseTask> getTasks(){
		return tasks;
	}
	
	/**
	 * 执行一个具体的任务
	 * @param task
	 * @param obj
	 * @return
	 * 		true	命中任务
	 * 		flase	未命中任务
	 */
	private boolean doTask( BaseTask task, Object obj ){
		if( task.doTask( user, obj ) ){
			if( task.getStatus() == TaskStatus.NO_REWARD ){
				finishTask( task );
			}
			return true;
		}
		return false;
	}
	
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
				tasks.put( t.getTempletId(), t.createTask() );
				//TODO 写入数据库
				//TODO 通知客户端
			}
		}
	}
	
	/**
	 * 返回一个copy供外层使用，确保hashMap内的数据不被发布到本类以外
	 * @param templetId
	 * @return
	 */
	public BaseTask getTaskCopyByTempletId( short templetId ) {
		BaseTask t = tasks.get( templetId );
		
		return tasks.get( templetId );
	}
	
	@Override
	public String toString () {	
		Object[] key_arr = tasks.keySet().toArray();  
		Arrays.sort( key_arr );  //排序先，否则打印出来顺序是乱的
		
		StringBuilder sb = new StringBuilder( "user=" + user.getName() );
		sb.append( ", tasks=[\n" );
		for( Object key : key_arr ) {  
		    
			BaseTask t = tasks.get( key );
			sb.append( "\t[id=" + t.getTemplet().getTempletId() );
			sb.append( ", name=" + t.getTemplet().getName() );
			sb.append( ",status=" + t.getStatus() );
			sb.append( ",taskType=" + t.getTemplet().getTaskType() );
			sb.append( "]\n" );
		}
		
		sb.append( "]" );
		return sb.toString();
	}
	public static void main ( String[] args ) {
		short templetId = 10000;
		BaseTaskTemplet t = TaskTempletCfg.getTempletById( templetId );
		BaseTask task = t.createTask();
		task.parseParamFromDb( "" );
	}
}
