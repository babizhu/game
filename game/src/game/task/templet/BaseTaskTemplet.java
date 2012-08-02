package game.task.templet;


public abstract class BaseTaskTemplet implements ITaskTemplet {

	/**
	 * 模版id
	 */
	int			templetId;

	public int getTempletId() {
		return templetId;
	}

	public void setTempletId(int templetId) {
		this.templetId = templetId;
	}
	
	

}
