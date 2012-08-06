package game.task.templet;

import java.util.Arrays;

import game.task.cfg.TaskTempletCfg;
import game.task.enums.TaskType;


public abstract class BaseTaskTemplet implements ITaskTemplet {

	/**
	 * 任务名称
	 */
	String			name;
	/**
	 * 任务类型
	 */
	TaskType		taskType;
	
	/**
	 * 模版id
	 */
	int				templetId;
	
	/**
	 * 后继任务的id数组，不存在为null
	 */
	
	/**
	 * 任务属性
	 */
	TaskProperty	taskProperty;
	
	/**
	 * 作为中间变量保存后继任务的id
	 */
	int[]		successorTempletId;
	
	/**
	 * 本任务完成后，要开启的任务，如果不存在则为null
	 */
	BaseTaskTemplet[]			successorTemplet;

	public int getTempletId() {
		return templetId;
	}

	public void setTempletId(int templetId) {
		this.templetId = templetId;
	}

	public TaskType getTaskType () {
		return taskType;
	}

	public void setTaskType ( TaskType type ) {
		this.taskType = type;
	}

	public BaseTaskTemplet[] getSuccessorTemplet() {
		return successorTemplet;
	}

	/**
	 * 构建后续任务模板
	 */
	public void buildSuccessorTemplet() {
		if( successorTempletId != null ){
			successorTemplet = new BaseTaskTemplet[successorTempletId.length];
			for( int i = 0; i < successorTempletId.length; i++ ){
				BaseTaskTemplet t = TaskTempletCfg.getTempletById( successorTempletId[i] );
				if( t == null ){
					throw new IllegalArgumentException( "模板id为" + successorTempletId[i] + "的后继任务无法找到" );
				}
				successorTemplet[i] = t;
				
			}
		}
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TaskProperty getTaskProperty() {
		return taskProperty;
	}

	public void setTaskProperty(TaskProperty taskProperty) {
		this.taskProperty = taskProperty;
	}
	
	

	/**
	 * 输入参数的格式为23,45,67，
	 * 完成本任务后设置id为23,45,67这三个任务为可接状态
	 * 
	 * 为什么需要设置这个，而不是直接设置一个BaseTaskTemplet数组
	 * 因为初始化的时候从配置表读取的后继任务模板，此时还没有初始化成功，
	 * 必须所有的任务模板都初始化之后才有可以得到后继任务
	 * @param param
	 */	
	public void setSuccessorTempletId( String param ) {
		if( param.isEmpty() ){
			return;
		}
		String str[] = param.split( "," );
		successorTempletId = new int[str.length];
		for( int i = 0; i < str.length; i++ ){
			successorTempletId[i] = Integer.parseInt( str[i] );
		}
	}

	@Override
	public String toString() {
		return "BaseTaskTemplet [name=" + name + ", type=" + taskType
				+ ", templetId=" + templetId + ", taskProperty=" + taskProperty
				+ ", successorTempletId=" + Arrays.toString(successorTempletId)
				+ ", successorTemplet="
				+ formatSuccessor()
				+ "]";
	}
	
	private String formatSuccessor(){
		if( successorTemplet == null ){
			return null;
		}
		String s = "[";
		for( BaseTaskTemplet t : successorTemplet ){
			s += t.name;
			s += ",";
		}
		s = s.substring( 0, s.length() - 1 );//去掉最后一个","
		s += "]";
		return s;
	}
	
	
	
	
}
