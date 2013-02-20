package game.task.templet;

import game.task.TaskBase;

public interface ITaskTemplet {

	/**
	 * 根据模板返回相应的任务类
	 * @return
	 */
	TaskBase createTask();
	
	/**
	 * 从配置表读取相应字符串并解析
	 * @param param
	 */
	void parseParam( String param );
}
