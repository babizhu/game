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
	 * 本任务完成后，要开启的任务，如果不存在则为null
	 */
	BaseTaskTemplet[]			openTempletId;

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

	public BaseTaskTemplet[] getOpenTempletId() {
		return openTempletId;
	}

	/**
	 * 输入参数的格式为23,45,67
	 * 完成本任务后设置id为23,45,67这三个任务为可接状态
	 * @param param
	 */
	public void setOpenTempletId( String param ) {
		String str[] = param.split( "," );
		openTempletId = new BaseTaskTemplet[str.length];
		for( int i = 0; i < str.length; i++ ){
			//openTempletId[i] = Integer.parseInt( str[i] );
		}
	}
	
	
	
	

}
