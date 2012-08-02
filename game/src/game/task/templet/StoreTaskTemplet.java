package game.task.templet;

import game.task.ITask;
import game.task.StoreTask;

/**
 * 店铺类任务
 * @author liukun
 *
 */
public class StoreTaskTemplet  extends BaseTaskTemplet{

	int 	count;
	int		storeLevel;

	@Override
	public ITask createTask() {
		return new StoreTask();
	}

	/**
	 * 格式为23,4
	 * 代表：玩家拥有23级的店铺4间
	 */
	@Override
	public void parseParam(String param) {
		// TODO Auto-generated method stub
		
	}
}
