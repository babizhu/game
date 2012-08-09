package game.task;


import game.task.enums.TaskStatus;
import user.UserInfo;
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

	@Override
	public boolean doTask ( UserInfo user, Object obj ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void parseParam ( String str ) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 不存在特殊参数则返回null
	 */
	@Override
	public String buildParam () {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
	
}
