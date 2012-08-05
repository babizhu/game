package game.task.templet;

import game.task.BaseTask;
import game.task.PropTask;

public class PropTaskTemplet extends BaseTaskTemplet{

	int				propId;
	int				needCount;
	@Override
	public BaseTask createTask() {
		return new PropTask( this );
	}

	/**
	 * 字符串格式：
	 * 100020345,10
	 * 收集id为100020345的道具10个
	 */
	@Override
	public void parseParam( String param ) {
		String p[] = param.split( "," );
		propId = Integer.parseInt( p[0] );
		needCount = Integer.parseInt( p[1] );
		
	}

	public int getPropId() {
		return propId;
	}

//	public void setPropId(int propId) {
//		this.propId = propId;
//	}

	public int getNeedCount() {
		return needCount;
	}

//	public void setNeedCount(int needCount) {
//		this.needCount = needCount;
//	}

	
}
