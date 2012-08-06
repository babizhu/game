package game.task.templet;

import game.task.BaseTask;
import game.task.DirectTask;
import game.task.enums.TaskType;

/**
 * 直接完成任务的模板
 * @author liukun
 *
 */
public class DirectTaskTemplet extends BaseTaskTemplet {

	
	@Override
	public BaseTask createTask() {
		return new DirectTask( this );
	}



	@Override
	public void parseParam(String param) {
		// TODO Auto-generated method stub
		
	}



	public DirectTaskTemplet() {
		this.setTaskType( TaskType.DIRECT );
	}

	
	
}
