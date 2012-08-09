package game.task;

import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;
import game.task.templet.MissionTaskTemplet;
import user.UserInfo;

public class MissionTask extends BaseTask {

	public MissionTask(MissionTaskTemplet missionTaskTemplet) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean doTask(UserInfo user, Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TaskType getTaskType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		// TODO Auto-generated method stub
		return null;
	}

}
