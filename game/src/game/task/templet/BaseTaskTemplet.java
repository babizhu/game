package game.task.templet;

import game.task.enums.TaskType;


public abstract class BaseTaskTemplet implements ITaskTemplet {

	/**
	 * 任务类型
	 */
	TaskType	type;
	
	/**
	 * 模版id
	 */
	int			templetId;
	
	/**
	 * 本任务完成后，开启的任务
	 */
	int			openTempletId;

	public int getTempletId() {
		return templetId;
	}

	public void setTempletId(int templetId) {
		this.templetId = templetId;
	}

	public TaskType getType () {
		return type;
	}

	public void setType ( TaskType type ) {
		this.type = type;
	}
	
	
	
	

}
