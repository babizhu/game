package game.task;

import game.task.enums.TaskType;
import game.task.templet.DirectTaskTemplet;

public class DirectTask extends BaseTask {

	private DirectTaskTemplet 	templet;
	
	@Override
	public void doTask ( Object obj ) {
		int taskId = (Integer) obj;
		if( taskId == templet.getTempletId() ){
			super.doTask(obj);
		}
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.DIRECT;
	}

}
