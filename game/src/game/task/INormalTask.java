package game.task;

import util.ErrorCode;

public interface INormalTask extends ITask {

	/**
	 * 玩家接受任务
	 * @param templetId
	 * @param user
	 * @return
	 */
	ErrorCode acceptTask( long templetId  );

}
