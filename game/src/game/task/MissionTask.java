package game.task;

import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;
import game.task.templet.MissionTaskTemplet;
import user.UserInfo;

/**
 * 闯关任务
 * @author admin
 *
 */
public class MissionTask extends BaseTask {

	MissionTaskTemplet templet;
	public MissionTask(MissionTaskTemplet missionTaskTemplet) {
		templet = missionTaskTemplet;
	}

	@Override
	public boolean doTask(UserInfo user, Object obj) {
		int missionId = (Integer) obj;
		if( missionId == templet.getMissionId() ){
			super.doneTask();
			return true;
		}
		return false;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.MISSION;
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		return templet;
	}
	
	public static void main ( String[] args ) {
//		Object obj = null;
//		int i = (Integer) obj;
	}

}
