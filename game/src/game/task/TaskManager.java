package game.task;

import java.util.HashMap;
import java.util.Map;

import util.ErrorCode;


public class TaskManager {
	private Map<Integer,TaskInfo> task = new HashMap<Integer, TaskInfo>();
	
	ErrorCode doTask( int taskTempletId ){
		TaskInfo t = task.get( taskTempletId );
		if( t != null && t.getStatus() == TaskStatus.ACCEPT ){
//			t.dotask();
			
			if( t.getStatus() == TaskStatus.FINISH ){
				
			}
		}
		
		
		return ErrorCode.UNKNOW_ERROR;
	}
	
	

}
