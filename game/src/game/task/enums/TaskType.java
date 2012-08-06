package game.task.enums;

import game.task.templet.BaseTaskTemplet;
import game.task.templet.DirectTaskTemplet;
import game.task.templet.PropTaskTemplet;

/**
 * 人物类型
 * @author liukun
 *
 */
public enum TaskType {
	
	/**
	 * 直接完成类任务
	 */
	DIRECT {
		@Override
		public BaseTaskTemplet createNewTemplet() {
			return new DirectTaskTemplet();
		}
	},
	
	/**
	
	 * 数据检测类任务
	 */
	COUNT {
		@Override
		public BaseTaskTemplet createNewTemplet() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	
	/**
	 * 道具检测类任务
	 */
	PROP {
		@Override
		public BaseTaskTemplet createNewTemplet() {
			return new PropTaskTemplet();
		}
	},
	
	/**
	 * 玩家自定义任务
	 */
	CUSTOM {
		@Override
		public BaseTaskTemplet createNewTemplet() {
			// TODO Auto-generated method stub
			return null;
		}
	};

	 public abstract BaseTaskTemplet createNewTemplet() ;
}
