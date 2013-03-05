package game.task;

import game.task.templet.TaskTempletBase;
import user.UserInfo;

public class CustomTask extends TaskBase implements ITask {

	public CustomTask( TaskTempletBase templet) {
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
	public TaskTempletBase getTemplet() {
		return null;
	}
}
