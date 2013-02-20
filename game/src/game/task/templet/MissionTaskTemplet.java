package game.task.templet;

import game.task.TaskBase;
import game.task.MissionTask;
import game.task.enums.TaskType;

/**
 * 关卡任务
 * @author liukun
 *
 */
public class MissionTaskTemplet extends BaseTaskTemplet{ 
	/**
	 * 任务id
	 */
	int		missionId;


	public MissionTaskTemplet() {
		this.setTaskType( TaskType.MISSION );
	}

	@Override
	public TaskBase createTask() {
		return new MissionTask( this );
	}
	
	/**
	 * 字符串格式：
	 * 10000
	 * 代表关卡id
	 */
	@Override
	public void parseParam(String param) {
		missionId = Integer.parseInt( param );		
	}
	
	
	
	public int getMissionId() {
		return missionId;
	}

	public String toString () {
		String s = super.toString();
		s = s.substring( 0, s.length() - 1 );//去掉"]"
		return s + ", missionId=" + missionId + "]";
	}
}
