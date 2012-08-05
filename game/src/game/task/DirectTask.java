package game.task;

import user.UserInfo;
import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;
import game.task.templet.DirectTaskTemplet;

public class DirectTask extends BaseTask {

	private DirectTaskTemplet 	templet;
	
	public DirectTask(DirectTaskTemplet templet) {
		this.templet = templet; 
	}

	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		int templetId = (Integer) obj;
		if( templetId == templet.getTempletId() ){
			super.finishTask();
			return true;
		}
		
		return false;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.DIRECT;
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		return templet;
	}
	
}
