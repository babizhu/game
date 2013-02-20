package game.task;

import game.task.templet.DirectTaskTemplet;
import user.UserInfo;

/**
 * 直接完成的任务，例如：
 * 1、寻访某人
 * 
 * @author liukun
 * 2012-9-21 下午05:24:52
 */
public class DirectTask extends TaskBase {

	
	public DirectTask( DirectTaskTemplet templet ) {
		super(templet);
	}

	/**
	 * @param	obj			任务的模板id
	 */
	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		int templetId = (Integer) obj;
		if( templetId == getTemplet().getTempletId() ){
			super.doneTask();
			return true;
		}
		return false;
	}
	
}
