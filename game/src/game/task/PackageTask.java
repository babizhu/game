package game.task;


import game.task.enums.TaskType;
import game.task.templet.PackageTaskTemplet;

/**
 * 物品栏采集任务
 * 例如收集n个商票
 * @author admin
 *
 */
public class PackageTask extends BaseTask {

	private PackageTaskTemplet 	templet;
	
	@Override
	public void doTask ( Object obj ) {
		int count = 90;//从背包获取道具的数量
		if( count >= templet.needCount ){
			super.finishTask();	
		}
	}
	
	@Override
	public TaskType getTaskType() {
		return TaskType.PACKAGE;
	}



}
