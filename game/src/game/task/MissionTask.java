package game.task;

import game.task.templet.BaseTaskTemplet;
import game.task.templet.MissionTaskTemplet;
import user.UserInfo;

/**
 * 闯关任务
 * @author admin
 *
 */
public class MissionTask extends BaseTask {

	private MissionTaskTemplet templet;
	
	public MissionTask(MissionTaskTemplet missionTaskTemplet) {
		templet = missionTaskTemplet;
	}

	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		int missionId = (Integer) obj;
		if( missionId == templet.getMissionId() ){
			super.doneTask();
			return true;
		}
		return false;
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		return templet;
	}

}
