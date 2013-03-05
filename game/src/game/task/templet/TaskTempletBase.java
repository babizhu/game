package game.task.templet;

import java.util.Arrays;

import game.task.cfg.TaskTempletCfg;
import game.task.enums.TaskProperty;
import game.task.enums.TaskType;


public abstract class TaskTempletBase implements ITaskTemplet {

	/**
	 * 接此任务所需要的等级
	 */
	short				requiredLevel;
	
	/**
	 * 任务名称
	 */
	String				name;
	
	/**
	 * 任务类型
	 */
	TaskType			taskType;
	
	/**
	 * 任务模版id
	 */
	short				templetId;
	
	
	/**
	 * 任务属性
	 */
	TaskProperty		taskProperty;
	
	/**
	 * 作为中间变量保存后继任务的id，不存在为null
	 */
	short[]				successorTempletId;
	
	/**
	 * 本任务完成后，要开启的后继任务，如果不存在则为null
	 */
	TaskTempletBase[]	successorTemplet;
	
	/**
	 * 判断此任务是否应该接了之后立刻检测是否完成
	 * 大多数的状态选择类任务都是如此，例如：
	 * 判断背包内的物品数量，在接任务的同时就有必要判断一次
	 * 是否领取工商执照
	 * 某某技能升级到某某级
	 * 
	 */
	private	boolean		checkNow;	
	
	private	GoalTemplet goal;
	/**
	 * 判断此任务在玩家接受的同时是否有必要立即检查一下
	 * @return
	 */
	public boolean isCheckNow () {
		return checkNow;
	}

	public void setCheckNow ( boolean checkNow ) {
		this.checkNow = checkNow;
	}

	public short getTempletId() {
		return templetId;
	}

	public void setTempletId( short templetId ) {
		this.templetId = templetId;
	}

	public TaskType getTaskType () {
		return taskType;
	}

	public void setTaskType ( TaskType type ) {
		this.taskType = type;
	}

	public TaskTempletBase[] getSuccessorTemplet() {
		return successorTemplet;
	}

	/**
	 * 构建后续任务模板
	 */
	public void buildSuccessorTemplet() {
		if( successorTempletId != null ){
			successorTemplet = new TaskTempletBase[successorTempletId.length];
			int i = 0;
			for( short s : successorTempletId ){
				TaskTempletBase t = TaskTempletCfg.getTempletById( s );
				if( t == null ){
					throw new IllegalArgumentException( "模板id为" + s + "的后继任务无法找到" );
				}
				successorTemplet[i++] = t;
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
		successorTempletId = new short[str.length];
		for( int i = 0; i < str.length; i++ ){
			successorTempletId[i] = Short.parseShort( str[i] );
		}
	}
	
	

	public short getRequiredLevel () {
		return requiredLevel;
	}

	public void setRequiredLevel ( short requiredLevel ) {
		this.requiredLevel = requiredLevel;
	}

	@Override
	public String toString() {
		return "BaseTaskTemplet [name=" + name + ", type=" + taskType
				+ ", templetId=" + templetId + ", taskProperty=" + taskProperty
				+ ", requiredLevel=" + requiredLevel
				+ ", checkNow=" + checkNow
				+ ", successorTempletId=" + Arrays.toString(successorTempletId)
				+ ", successorTemplet="
				+ formatSuccessor()
				+ "]";
	}
	
	/**
	 * 避免toString()函数中的递归调用，导致字符串很长打印出来没法看
	 * @return
	 */
	private String formatSuccessor(){
		if( successorTemplet == null ){
			return null;
		}
		StringBuilder sb = new StringBuilder( "[" );
		for( TaskTempletBase t : successorTemplet ){
			sb.append( t.name + "," );
		}
		String s = sb.toString();
		s = s.substring( 0, s.length() - 1 );//去掉最后一个","
		s += "]";
		return s;
	}

	public GoalTemplet getGoal() {
		return goal;
	}

	public void setGoal(GoalTemplet goal) {
		this.goal = goal;
	}
	
	
	
	
}
