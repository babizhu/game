package game.task;

import game.task.templet.MissionTaskTemplet;
import user.UserInfo;

/**
 * 通常是主线的闯关任务
 * 
 * @author liukun
 * 2012-9-21 下午06:12:28
 * 
 * 
 */

public class MissionTask extends TaskBase {

	
	public MissionTask(MissionTaskTemplet missionTaskTemplet) {
		super(missionTaskTemplet);
	}

	/**
	 * @param 	obj			关卡id
	 */
	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		int missionId = (Integer) obj;
		MissionTaskTemplet mTemplet = (MissionTaskTemplet) getTemplet();
		if( missionId == mTemplet.getMissionId() ){
			super.doneTask();
			return true;
		}
		return false;
	}
}
