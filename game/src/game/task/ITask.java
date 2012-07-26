package game.task;

import user.UserInfo;
import util.ErrorCode;

public interface ITask {
	/**
	 * 进行一次任务
	 * @param id
	 */
	void doTask( long templetId );
	
	/**
	 * 玩家接受任务
	 * @param templetId
	 * @param user
	 * @return
	 */
	ErrorCode acceptTask( long templetId, UserInfo user );

}
