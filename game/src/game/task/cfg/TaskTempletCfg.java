package game.task.cfg;

import game.task.templet.BaseTaskTemplet;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 从配置表中初始化模板
 * @author liukun
 *
 */
public class TaskTempletCfg {
	private static final Map<Integer,BaseTaskTemplet> taskTemplet;
	
	static{
		taskTemplet = new HashMap<Integer, BaseTaskTemplet>();
		init();
		
	}
	
	/**
	 * 通过配置表读取任务模板
	 */
	private static void init(){
		
		
		//TODO 读取内容
		buildOpenTemplet();
		
	}
	
	/**
	 * 所有任务模板从配置表读取后，才能初始化后继任务
	 */
	private static void buildOpenTemplet(){
		for( Entry<Integer, BaseTaskTemplet> e : taskTemplet.entrySet() ){
			e.getValue().buildOpenTemplet();
		}
	}
	/**
	 * 通过模板id获取模板
	 * @param id
	 * @return
	 */
	public static BaseTaskTemplet getTempletById( int id ){
		return taskTemplet.get( id );
	}
	

}
