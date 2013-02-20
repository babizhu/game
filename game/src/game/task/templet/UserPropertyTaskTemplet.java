package game.task.templet;

import game.task.TaskBase;
import game.task.UserPropertyTask;
import game.task.enums.TaskType;

/**
 * 玩家属性任务
 * @author liukun
 *
 */
public class UserPropertyTaskTemplet extends BaseTaskTemplet{ 
	/**
	 * 任务id
	 */
	int		property;		//属性类型，例如hp，攻击力等
	int		needNumber;		//所需要达到的参数	


	public UserPropertyTaskTemplet() {
		this.setTaskType( TaskType.MISSION );
	}

	@Override
	public TaskBase createTask() {
		return new UserPropertyTask( this );
	}
	
	/**
	 * 字符串格式：
	 * 1,10000
	 * 此玩家的的hp（1）达到10000
	 */
	@Override
	public void parseParam(String param) {
		if( param.isEmpty() ){
			return;
		}
		String p[] = param.split( "," );
		property = Integer.parseInt( p[0] );
		needNumber = Integer.parseInt( p[1] );
	}
	
	
	
	public int getNeedNumber() {
		return needNumber;
	}

	
	public int getProperty() {
		return property;
	}


	public String toString () {
		String s = super.toString();
		s = s.substring( 0, s.length() - 1 );//去掉"]"
		return s + ", property=" + property + ", needCount=" + needNumber + "]";
	}
}
