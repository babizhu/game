package game.task.templet;

import game.task.TaskBase;
import game.task.DirectTask;
import game.task.enums.TaskType;

/**
 * 直接完成任务的模板
 * @author liukun
 *
 */
public class DirectTaskTemplet extends TaskTempletBase {

	public DirectTaskTemplet() {
		this.setTaskType( TaskType.DIRECT );
	}
	
	@Override
	public TaskBase createTask() {
		return new DirectTask( this );
	}

	@Override
	public void parseParam(String param) {
		
	}
}
