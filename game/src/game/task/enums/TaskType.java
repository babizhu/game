package game.task.enums;

import game.task.templet.BaseTaskTemplet;
import game.task.templet.DirectCountTaskTemplet;
import game.task.templet.DirectTaskTemplet;
import game.task.templet.MissionTaskTemplet;
import game.task.templet.PropTaskTemplet;

/**
 * 任务类型
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
	 * 关卡任务
	 */
	MISSION{

		@Override
		public BaseTaskTemplet createNewTemplet() {
			return new MissionTaskTemplet();
		}
		
	},
	
	/**
	 *直接完成类的计数任务
	 */
	DIRECT_COUNT{

		@Override
		public BaseTaskTemplet createNewTemplet() {
			return new DirectCountTaskTemplet();
		}
		
	},
	
	/**
	 * 玩家自定义任务
	 */
	CUSTOM {
		@Override
		public BaseTaskTemplet createNewTemplet() {
			return null;
		}
	};

	 public abstract BaseTaskTemplet createNewTemplet() ;
}
