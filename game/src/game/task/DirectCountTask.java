package game.task;

import game.task.templet.DirectCountTaskTemplet;
import user.UserInfo;

/**
 * 直接计数类任务，例如：<br>
 * 
 * 1、扔垃圾十次
 * 2、挑战某个关卡10次
 *   
 * @author admin
 * 2012-9-21 下午12:01:33
 */
public class DirectCountTask extends TaskBase {

	
	/**
	 * 此任务已经完成的次数
	 */	
	private	int						count;
	
	public DirectCountTask( DirectCountTaskTemplet templet ) {
		super( templet );
	}
	
	/**
	 * @param		obj			任务模板id
	 */
	@Override
	public boolean doTask( UserInfo user, Object obj ) {
		int templetId = (Integer) obj;
		if( templetId == getTemplet().getTempletId() ){
			count++;
			DirectCountTaskTemplet dTemplet = (DirectCountTaskTemplet)getTemplet(); 
			if( count >= dTemplet.getNeedCount() ){
				super.doneTask();
			}
			return true;
		}
		return false;
	}


	@Override
	public void parseParamFromStr ( String str ) {
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
		TaskBase bt = (TaskBase) ot;
		System.out.println( t.getParam() );
		System.out.println( bt.getParam() );
	}
	
	public void copy( TaskBase t ) {
		super.copy(t);
		this.count =(Integer) t.getParam();
	}
}
