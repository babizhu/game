package game.task;

import game.task.templet.BaseTaskTemplet;
import game.task.templet.DirectTaskTemplet;
import user.UserInfo;

public class DirectTask extends BaseTask {

	private DirectTaskTemplet 	templet;
	
	public DirectTask( DirectTaskTemplet templet ) {
		this.templet = templet; 
	}

	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		int templetId = (Integer) obj;
		if( templetId == templet.getTempletId() ){
			super.doneTask();
			return true;
		}
		return false;
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		return templet;
	}
	
}
