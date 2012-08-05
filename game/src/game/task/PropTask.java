package game.task;

import game.task.enums.TaskType;
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
		int propId = (Integer) obj;
		if( templet.getPropId() == propId ){
			int count = 50;//50从背包中获取
			if( count >= templet.getNeedCount() ){//任务完成
				finishTask();
				return true;
			}			
		}	
		return false;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.PROP;
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		return templet;
	}

}
