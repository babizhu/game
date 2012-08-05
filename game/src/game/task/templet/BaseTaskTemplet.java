package game.task.templet;

import game.task.cfg.TaskTempletCfg;
import game.task.enums.TaskType;


public abstract class BaseTaskTemplet implements ITaskTemplet {

	/**
	 * 任务类型
	 */
	TaskType	type;
	
	/**
	 * 模版id
	 */
	int			templetId;
	
	/**
	 * 后继任务的id数组，不存在为null
	 */
	
	int[]		openTempletId;
	/**
	 * 本任务完成后，要开启的任务，如果不存在则为null
	 */
	BaseTaskTemplet[]			openTemplet;

	public int getTempletId() {
		return templetId;
	}

	public void setTempletId(int templetId) {
		this.templetId = templetId;
	}

	public TaskType getType () {
		return type;
	}

	public void setType ( TaskType type ) {
		this.type = type;
	}

	public BaseTaskTemplet[] getOpenTemplet() {
		return openTemplet;
	}

	public void buildOpenTemplet() {
		if( openTempletId != null ){
			openTemplet = new BaseTaskTemplet[openTempletId.length];
			for( int i = 0; i < openTempletId.length; i++ ){
				BaseTaskTemplet t = TaskTempletCfg.getTempletById( openTempletId[i] );
				if( t == null ){
					throw new IllegalArgumentException( "模板id为" + openTempletId[i] + "的后继任务无法找到" );
				}
				openTemplet[i] = t;
				
			}
		}
	}

//	public int[] getOpenTempletId() {
//		return openTempletId;
//	}

	/**
	 * 输入参数的格式为23,45,67，
	 * 完成本任务后设置id为23,45,67这三个任务为可接状态
	 * 
	 * 为什么需要设置这个，而不是直接设置一个BaseTaskTemplet数组
	 * 因为初始化的时候从配置表读取的后继任务模板，此时还没有初始化成功，
	 * 必须所有的任务模板都初始化之后才有可以得到后继任务
	 * @param param
	 */	
	public void setOpenTempletId( String param ) {
		String str[] = param.split( "," );
		openTempletId = new int[str.length];
		for( int i = 0; i < str.length; i++ ){
			openTempletId[i] = Integer.parseInt( str[i] );
		}
	}
	
}
