package game.task;

import game.task.templet.BaseTaskTemplet;
import game.task.templet.DirectCountTaskTemplet;
import user.UserInfo;

/**
 * 直接计数类任务，例如：<br>
 * 
 * 1、扔垃圾十次，
 *   
 * @author admin
 * 2012-9-21 下午12:01:33
 */
public class DirectCountTask extends BaseTask {

	private DirectCountTaskTemplet 	templet;
	
	/**
	 * 此任务已经完成的次数
	 */	
	private	int						count;
	
	public DirectCountTask( DirectCountTaskTemplet templet ) {
		this.templet = templet;
	}
	
	/**
	 * @param		obj			任务模板id
	 */
	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		int templetId = (Integer) obj;
		if( templetId == templet.getTempletId() ){
			count++;
			if( count >= templet.getNeedCount() ){
				super.doneTask();
			}
			return true;
		}
		return false;
	}

	@Override
	public BaseTaskTemplet getTemplet() {
		return templet;
	}
	
	@Override
	public void parseParamFromDb ( String str ) {
		count = Integer.parseInt( str );
	}

	/**
	 * 不存在特殊参数则返回null
	 */
	@Override
	public Object getParam( ){
		return count;
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		s = s.substring( 0, s.length() - 1 );//去掉"]"
		return s + ", count=" + count + "]";
	}
	public static void main(String[] args) {
		DirectCountTask t = new DirectCountTask(null);
		Object ot = t;
		BaseTask bt = (BaseTask) ot;
		System.out.println( t.getParam() );
		System.out.println( bt.getParam() );
	}
}
