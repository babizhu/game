package game.task.templet;

import game.task.TaskBase;
import game.task.CustomTask;

/**
 * 玩家定制任务
 * @author liukun
 *
 */
public class CustomTaskTemplet extends BaseTaskTemplet {

	@Override
	public TaskBase createTask() {
		return new CustomTask( this );
	}


	@Override
	public void parseParam(String param) {
		
		
	}

}
