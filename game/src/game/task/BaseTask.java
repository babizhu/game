package game.task;

import game.task.enums.TaskStatus;
import game.task.templet.BaseTaskTemplet;
import user.UserInfo;
import util.BaseUtil;
import util.SystemTimer;


/**
 * 保存玩家的任务bean信息
 * 
 * @author liukun
 * 
 */
public  class BaseTask implements ITask{
	
	
	private BaseTaskTemplet	templet;
	/**
	 * 任务流水id
	 */
	//private long	id;
	
	/**
	 * 接任务的时间
	 */
	private int	acceptSec; 	
	
	/**
	 * 完成任务时间，此时尚未领奖
	 */
	private int	doneSec;
	
	/**
	 * 领奖时间
	 */
	private	int	acceptAwardSec;	
	
	/**
	 * 此任务的状态
	 */
	private TaskStatus status;
	
	
	public TaskStatus getStatus() {
		return status;
	}

	public BaseTask(BaseTaskTemplet templet) {
		this.status = TaskStatus.CAN_ACCEPT;
		this.templet = templet;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public int getAcceptSec () {
		return acceptSec;
	}

	public void setAcceptSec ( int acceptTime ) {
		this.acceptSec = acceptTime;
	}

	public int getDoneSec () {
		return doneSec;
	}

	public void setDoneSec ( int doneSec ) {
		this.doneSec = doneSec;
	}

//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}

	public int getAcceptAwardSec () {
		return acceptAwardSec;
	}


	public void setAcceptAwardSec ( int awardSec ) {
		this.acceptAwardSec = awardSec;
	}

	/**
	 * 完成任务时候需要做的通用操作
	 */
	void doneTask(){
		setStatus( TaskStatus.NO_REWARD );
		setDoneSec( SystemTimer.currentTimeSecond() );
	}

	@Override
	public boolean doTask ( UserInfo user, Object obj ) {
		return false;
	}

	@Override
	public void parseParamFromStr ( String str ) {
		
	}

	/**
	 * 不存在特殊参数则返回null
	 */
	@Override
	public Object getParam( ){
		return null;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [ templetId=" + templet.getTempletId() + ", acceptSec=" + BaseUtil.secondsToDateStr( acceptSec )
				+ ", doneSec=" + BaseUtil.secondsToDateStr( doneSec ) + ", acceptAwardSec="
				+ BaseUtil.secondsToDateStr( acceptAwardSec ) + ", status=" + status + "]";
	}
	
	public static void main(String[] args) {
	}

	public void copy( BaseTask t ) {
		this.acceptAwardSec = t.acceptAwardSec;
		this.acceptSec = t.acceptSec;
		this.doneSec = t.doneSec;
		this.status = t.status;
		this.templet = t.templet;
		//this.param = t.param;
		
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		return templet;

	}
	

	
	
	
}
