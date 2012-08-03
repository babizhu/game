package game.task.templet;

import game.task.ITask;
import game.task.PackageTask;
import game.task.enums.CheckTaskType;

public class PackageTaskTemplet  extends BaseTaskTemplet {

	/**
	 * 检测类任务所需要检测的类型
	 */
	CheckTaskType	objectType			=	CheckTaskType.PACKAGE;
	/**
	 * 所需要收集的道具ID的数量
	 */
	public final int		needCount 	= 	20;
	
	/**
	 * 所需要收集的道具ID
	 */
	public final int		propId		=	100101;

	@Override
	public ITask createTask() {
		return new PackageTask();
	}

	/**
	 * 格式为1023555,4
	 * 代表：玩家拥有道具类型id为1023555的道具4个
	 */
	@Override
	public void parseParam(String param) {
		// TODO Auto-generated method stub
		
	}
	
}
