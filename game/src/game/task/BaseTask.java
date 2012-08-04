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
	 * 任务流水id
	 */
	private long	id;
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
	 * 此任务的状态
	 */
	private TaskStatus status;
	
	
//	private BaseTaskTemplet	templet;

//	/**
//	 * 唯一id
//	 */
//	private long id;

	
	
	public TaskStatus getStatus() {
		return status;
	}

	public BaseTask() {
		super();
		this.status = TaskStatus.CAN_ACCEPT;
		//this.templet = templet;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAwardTime () {
		return awardTime;
	}

	public void setAwardTime ( long awardTime ) {
		this.awardTime = awardTime;
	}

	/**
	 * 完成任务时候需要做的通用任务
	 */
	void finishTask(){
		setStatus( TaskStatus.FINISH );
		setFinishTime( SystemTimer.currentTimeMillis() );
	}
	
}
