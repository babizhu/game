package game.task.templet;

import game.task.BaseTask;
import game.task.CustomTask;

/**
 * 玩家定制任务
 * @author liukun
 *
 */
public class CustomTaskTemplet extends BaseTaskTemplet {

	@Override
	public BaseTask createTask() {
		return new CustomTask( this );
	}


	@Override
	public void parseParam(String param) {
		
		
	}

}
