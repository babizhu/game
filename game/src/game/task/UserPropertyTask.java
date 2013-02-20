package game.task;

import game.task.templet.UserPropertyTaskTemplet;
import user.UserInfo;

/**
 * 检测玩家属性的任务，例如
 * 检测玩家的hp是否达到3000
 * 
 * @author liukun
 * 2012-8-22 下午02:49:05
 */
public class UserPropertyTask extends TaskBase {

	public UserPropertyTask(UserPropertyTaskTemplet templet) {
		super(templet);
	}
	/**
	 * obj可以是包含两个变量的数组，一个是属性，一个是此属性需要达到的数值
	 */
	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		Object[] parm = (Object[]) obj;
		int property = (Integer) parm[0]; 
		UserPropertyTaskTemplet uTemplet = (UserPropertyTaskTemplet) getTemplet();
		if( property == uTemplet.getProperty() ){
			
			//通过obj的第二个参数获取被改变的战士
			//判断此此战士是否完成任务
			System.out.println( );
		}
			
		return false;
	}

}
