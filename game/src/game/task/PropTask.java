package game.task;

import game.task.templet.BaseTaskTemplet;
import game.task.templet.PropTaskTemplet;
import user.UserInfo;

/**
 * 道具检测类任务
 * @author liukun
 *
 */
public class PropTask extends BaseTask {

	private PropTaskTemplet		templet;
	public PropTask( PropTaskTemplet templet) {
		super( );
		this.templet = templet;
	}

	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		
		int propId = (obj == null) ? templet.getPropId() : (Integer) obj;
		
		if( templet.getPropId() == propId ){
			int count = 50;//50从背包中获取，伪代码如下：
			//propId = (Integer)obj
			//int count = bag.getCount( propId );
			if( count >= templet.getNeedCount() ){//任务完成
				doneTask();
				return true;
			}			
		}	
		return false;
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		return templet;
	}

}
