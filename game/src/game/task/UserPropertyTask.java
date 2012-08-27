package game.task;

import user.UserInfo;
import game.task.templet.BaseTaskTemplet;
import game.task.templet.UserPropertyTaskTemplet;

/**
 * 检测玩家属性的任务
 * 
 * @author liukun
 * 2012-8-22 下午02:49:05
 */
public class UserPropertyTask extends BaseTask {

	private UserPropertyTaskTemplet		templet;
	public UserPropertyTask(UserPropertyTaskTemplet templet) {
		this.templet = templet;
	}
	/**
	 * obj可以是包含两个变量的数组
	 */
	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		Object[] parm = (Object[]) obj;
		//int property = 
		if( (Integer) parm[0] == templet.getProperty() ){
			
			//通过obj的第二个参数获取被改变的战士
			//判断此此战士是否完成任务
		}
			
		return false;
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		return templet;
	}
}
