package game.task;

import game.task.templet.PropTaskTemplet;
import user.UserInfo;

/**
 * 道具检测类任务
 * @author liukun
 *
 */
public class PropTask extends BaseTask {

	public PropTask( PropTaskTemplet templet) {
		super( templet );
	}

	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		PropTaskTemplet pTemplet = (PropTaskTemplet)getTemplet();
		int propId = (obj == null) ? 0 : (Integer) obj;
		System.out.println( propId );
		int count = 50;
		//50应从背包中获取，伪代码如下：
		//int count = bag.getCount( propId );
		if( count >= pTemplet.getNeedCount() ){//任务完成
			doneTask();
			return true;
		}			
			
		return false;
	}
	
	
}
