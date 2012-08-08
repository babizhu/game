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
	 * 完成任务时间，此时尚未领奖
	 */
	private long	doneTime;
	
	/**
	 * 领奖时间
	 */
	private	long	acceptAwardTime;	
	
	/**
	 * 此任务的状态
	 */
	private TaskStatus status;
	
	/**
	 * 判断在接任务的同时就必须检查任务是否完成
	 * 大多数的状态选择类任务都是如此，例如：
	 * 判断背包内的物品数量
	 * 是否领取工商执照
	 * 
	 */
	private	boolean	isCheckNow;	
	
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

	public long getDoneTime () {
		return doneTime;
	}

	public void setDoneTime ( long finishTime ) {
		this.doneTime = finishTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAcceptAwardTime () {
		return acceptAwardTime;
	}

	public void setAcceptAwardTime ( long awardTime ) {
		this.acceptAwardTime = awardTime;
	}

	/**
	 * 完成任务时候需要做的通用任务
	 */
	void doneTask(){
		setStatus( TaskStatus.NO_REWARD );
		setDoneTime( SystemTimer.currentTimeMillis() );
	}
	
}
