package game.task;

import java.util.HashMap;
import java.util.Map;

import util.ErrorCode;


public class TaskManager {
	private Map<Integer,BaseTask> task = new HashMap<Integer, BaseTask>();
	
//	/**
//	 * 客户端知道任务ID，传进来，无需其他参数配合可以直接完成
//	 * @param taskTempletId
//	 * @return
//	 */
//	ErrorCode doDirectTask( int taskTempletId ){
//		TaskInfo t = task.get( taskTempletId );
//		if( t != null && t.getStatus() == TaskStatus.ACCEPT ){
////			t.dotask();
//			
//			if( t.getStatus() == TaskStatus.FINISH ){
//				
//			}
//		}
//		
//		
//		return ErrorCode.UNKNOW_ERROR;
//	}
//	
//	/**
//	 * 获取准确的task id
//	 * @return
//	 */
//	public int getTaskId( TaskStatus ts, Object parm ){
//		return 0;
//	}
//	public ErrorCode doCollectTask( ){
//		return null;
//		
//	}
	ErrorCode doTask( Object obj ){
		return null;
		
	}
	
	
	

}
