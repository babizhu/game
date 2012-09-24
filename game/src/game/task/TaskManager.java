package game.task;

import game.task.cfg.TaskTempletCfg;
import game.task.enums.TaskStatus;
import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import user.UserInfo;
import util.ErrorCode;
import util.SystemTimer;

/**
 * 玩家任务管理器
 * @author liukun
 * 2012-9-21 下午05:45:18
 * 
 * 同步的思考：<br>
 * 让此类自身成为线程安全的
 */
public class TaskManager {

	/**
	 * 为什么要用map，不用list，请参考 
	 * {@link test.PerfomanceTest}.
	 * 
	 * @param		Short				为任务的模板id
	 * @param		BaseTask			所接任务
	 * 
	 */
	private ConcurrentHashMap<Short,BaseTask> tasks = new ConcurrentHashMap<Short, BaseTask>();
//	private List<BaseTask> 	tasks = new LinkedList<BaseTask>();
	private UserInfo		user;

	private final TaskDataProvider db = TaskDataProvider.getInstance();	

	public TaskManager( UserInfo user ) {
		super();
		this.user = user;
	}
	
	/**
	 * 从可接任务中，接一个新任务。<br>
	 * 如果新任务是“接时检测”类型的，则立即检测
	 * @param taskId
	 * @return
	 */
	public ErrorCode acceptTask( short templetId ){
		BaseTask task = tasks.get( templetId );
		if( task == null ){
			return ErrorCode.TASK_NOT_FOUND;
		}
		if( user.getLevel() < task.getTemplet().getNeedLevel() ){
			return ErrorCode.LEVEL_NOT_ENOUGH;
		}
		synchronized (task) {
			
			if( task.getStatus() != TaskStatus.CAN_ACCEPT ){
				return ErrorCode.TASK_HAS_ACCEPT;
			}
			
			task.setStatus( TaskStatus.ACCEPT );
			task.setAcceptSec( SystemTimer.currentTimeSecond() );
			
			if( task.getTemplet().isCheckNow() ){
				doTask( task, null );
			}
			
			db.update( task, user.getName() );
		}
		//TODO 通知前端
		return ErrorCode.SUCCESS;
	}
	
	public ErrorCode acceptAward( short templetId ){
		BaseTask task = tasks.get( templetId );
		if( task == null ){
			return ErrorCode.TASK_NOT_FOUND;
		}
		synchronized (task) {
			if( task.getStatus() != TaskStatus.NO_REWARD ){
				return ErrorCode.UNKNOW_ERROR;
			}
			task.setStatus( TaskStatus.FINISH );
			task.setAcceptAwardSec( SystemTimer.currentTimeSecond() );
			db.update( task, user.getName() );
		}
		tasks.remove( templetId );
		
		//TODO 玩家领奖
		//TODO 通知前端
		
		return ErrorCode.SUCCESS;		
	}
	/**
	 * 完成一次任务，限制如下：<br>
	 * <br>
	 * 用户的待完成的任务中，不允许存在，同种类型（），而且参数也相同的任务，例如：<br>
	 * 如果存在收集10个白色气球的任务，就不允许同时存在收取20个白色气球的任务，而收集黑色气球的任务则可以
	 * 
	 * @param type
	 * @param obj		完成任务所需要的参数
	 * @return
	 */
	public ErrorCode doTask( TaskType type, Object obj ){
		for( BaseTask t : tasks.values() ){
			
			synchronized ( t ) {				
				if( t.getStatus() == TaskStatus.ACCEPT && t.getTemplet().getTaskType() == type ){
					
					if( doTask( t, obj ) ){//限制：一次只允许完成一个任务，请策划确保不会出现两个需求相同的任务
						return ErrorCode.SUCCESS;					
					}
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
	public void addFirstTask(  ){
		BaseTaskTemplet templet = TaskTempletCfg.getTempletById( TaskTempletCfg.FIRST_TASK_ID );
		BaseTask task = templet.createTask();
		task.setStatus( TaskStatus.ACCEPT );//第一个任务缺省设置为已接状态
		task.setAcceptSec( SystemTimer.currentTimeSecond() );
		ErrorCode code = db.create( task, user.getName() );
		if( code == ErrorCode.SUCCESS ){
			tasks.putIfAbsent( templet.getTempletId(), task );
		}
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
			db.update( task, user.getName() );
			return true;
		}
		return false;
	}
	
	
	/**
	 * 此方法应该为私有，但为了test方便，暂时用缺省的安全机制
	 * @return
	 */
	Map<Short,BaseTask> getTasks(){
		return tasks;
	}
	
	/**
	 * 某个任务完成之后的后续工作，例如发送信息到客户端，etc
	 * 
	 * @param task			已完成的任务
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
		
		BaseTaskTemplet[] successor = templet.getSuccessorTemplet();
		if( successor != null ){
			 
			for( BaseTaskTemplet s : successor ){
				BaseTask newTask = s.createTask();
				db.create( newTask, user.getName() );
				tasks.putIfAbsent( s.getTempletId(), newTask );
				//TODO 通知客户端
			}
		}
	}
	
	/**
	 * 返回一个copy供外层使用，确保hashMap内的数据不被发布到本类以外
	 * @param templetId
	 * @return
	 */
	BaseTask getTaskCopyByTempletId( short templetId ) {
		//BaseTask t = tasks.get( templetId );
		
		return tasks.get( templetId );
	}
	
	/**
	 * 这里可能会出现同步问题，通常只在调试期间调用
	 */
	@Override
	public String toString () {	
		Object[] key_arr = tasks.keySet().toArray();  
		Arrays.sort( key_arr );  //排序先，否则打印出来顺序是乱的
		
		StringBuilder sb = new StringBuilder( "user=" + user.getName() );
		sb.append( ", tasks=[\n" );
		for( Object key : key_arr ) {  
		    
			BaseTask t = tasks.get( key );
			if( t == null ){
				throw new IllegalArgumentException( "不存在的任务" ); 
			}
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
