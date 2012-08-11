package game.task.templet;

import game.task.BaseTask;
import game.task.DirectCountTask;

/**
 * 直接完成的统计类任务模板
 * 例如：完成10次丢垃圾
 * @author liukun
 *
 */
public class DirectCountTaskTemplet extends BaseTaskTemplet {
	
	private int			needCount;
	@Override
	public BaseTask createTask() {
		return new DirectCountTask( this );
	}

	/**
	 * 字符串格式：
	 * 10
	 * 完成某种操作10次
	 */
	@Override
	public void parseParam( String param ) {
		needCount = Integer.parseInt( param );		
	}
	
	public int getNeedCount() {
		return needCount;
	}

}
