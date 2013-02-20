package game.task.templet;

import game.task.TaskBase;
import game.task.PropTask;
import game.task.enums.TaskType;

public class PropTaskTemplet extends BaseTaskTemplet{

	private int				propId;
	private int				needCount;
	@Override
	public TaskBase createTask() {
		return new PropTask( this );
	}

	/**
	 * 字符串格式：
	 * 100020345,10
	 * 收集id为100020345的道具10个
	 */
	@Override
	public void parseParam( String param ) {
		if( param.isEmpty() ){
			return;
		}
		String p[] = param.split( "," );
		propId = Integer.parseInt( p[0] );
		needCount = Integer.parseInt( p[1] );
		
	}

	public int getPropId() {
		return propId;
	}


	public int getNeedCount() {
		return needCount;
	}

	public PropTaskTemplet() {
		this.setTaskType( TaskType.PROP );
	}

	@Override
	public String toString () {
		String s = super.toString();
		s = s.substring( 0, s.length() - 1 );//去掉"]"
		return s + ", propId=" + propId + ", needCount=" + needCount + "]";
	}
	
}
