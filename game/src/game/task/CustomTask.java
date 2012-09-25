package game.task;

import game.task.templet.BaseTaskTemplet;
import user.UserInfo;

public class CustomTask extends BaseTask implements ITask {

	public CustomTask( BaseTaskTemplet templet) {
		super( templet );
	}

	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		//long taskId = (Long)obj;
//		if( taskId == this.getId() ){
//			doneTask();
//		}
		return true;
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		return null;
	}
}
