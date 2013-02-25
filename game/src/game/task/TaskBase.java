package game.task;

import java.nio.ByteBuffer;

import game.ITransformStream;
import game.task.enums.TaskStatus;
import game.task.templet.BaseTaskTemplet;
import user.UserInfo;
import util.UtilBase;
import util.ErrorCode;
import util.SystemTimer;


/**
 * 保存玩家的任务bean信息
 * 
 * @author liukun
 * 
 */
public  class TaskBase implements ITask, ITransformStream{
	
	
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

	public TaskBase(BaseTaskTemplet templet) {
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
		return getClass().getSimpleName() + " [ templetId=" + templet.getTempletId() + ", acceptSec=" + UtilBase.secondsToDateStr( acceptSec )
				+ ", doneSec=" + UtilBase.secondsToDateStr( doneSec ) + ", acceptAwardSec="
				+ UtilBase.secondsToDateStr( acceptAwardSec ) + ", status=" + status + "]";
	}
	
	public static void main(String[] args) {
	}

	void copy( TaskBase t ) {
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

	/**
	 * 最简单的接任务条件，
	 * 1、比较一下等级
	 * 2、检测状态
	 * 
	 * 其他某个特殊条件留待具体任务自行重载进行扩展
	 */
	@Override
	public ErrorCode acceptTask( UserInfo user ) {
		if( user.getLevel() < getTemplet().getRequiredLevel() ){
			return ErrorCode.USER_LEVEL_NOT_ENOUGH;
		}
		if( getStatus() != TaskStatus.CAN_ACCEPT ){
			return ErrorCode.TASK_HAS_ACCEPTED;
		}  
		return ErrorCode.SUCCESS;
	}

	@Override
	public void buildTransformStream( ByteBuffer buf ) {
		buf.putShort( getTemplet().getTempletId() );
		buf.put( getStatus().toNum() );
		String param = getParam() == null ? "" : getParam().toString();
		UtilBase.encodeString( buf, param );
		
	}
	

	
	
	
}
