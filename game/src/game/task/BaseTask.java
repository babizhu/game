package game.task;


import game.task.enums.TaskStatus;
import util.SystemTimer;



/**
 * 保存玩家的任务bean信息
 * 
 * @author liukun
 * 
 */
public abstract class BaseTask implements ITask{
	
	/**
	 * 接任务的时间
	 */
	private long	acceptTime; 	
	
	/**
	 * 完成任务时间
	 */
	private long	finishTime;
	
	/**
	 * 领奖时间
	 */
	private	long	awardTime;
	
	
	/**
	 * 所接任务的状态
	 */
	private TaskStatus status;

//	/**
//	 * 唯一id
//	 */
//	private long id;

	
	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public long getAcceptTime () {
		return acceptTime;
	}

	public void setAcceptTime ( long acceptTime ) {
		this.acceptTime = acceptTime;
	}

	public long getFinishTime () {
		return finishTime;
	}

	public void setFinishTime ( long finishTime ) {
		this.finishTime = finishTime;
	}

	public long getAwardTime () {
		return awardTime;
	}

	public void setAwardTime ( long awardTime ) {
		this.awardTime = awardTime;
	}

	@Override
	public void doTask ( Object obj ) {
		setStatus( TaskStatus.FINISH );
		setFinishTime( SystemTimer.currentTimeMillis() );
		
	}

	/**
	 * 完成任务时候需要做的通用任务
	 */
	void finishTask(){
		setStatus( TaskStatus.FINISH );
		setFinishTime( SystemTimer.currentTimeMillis() );
	}
	
}
