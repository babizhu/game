package game.task;

import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;
import user.UserInfo;

public class CustomTask extends BaseTask implements ITask {

	public CustomTask( BaseTaskTemplet templet) {
		super(  );
	}

	@Override
	public boolean doTask(UserInfo user, Object obj) {
		long taskId = (Long)obj;
		if( taskId == this.getId() ){
			doneTask();
		}
		return true;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.CUSTOM;
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		// TODO Auto-generated method stub
		return null;
	}

}
