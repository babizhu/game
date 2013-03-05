package game.task.enums;

import game.task.templet.TaskTempletBase;
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
		public TaskTempletBase createNewTemplet() {
			return new DirectTaskTemplet();
		}
	},
	
	/**
	
	 * 数据检测类任务
	 */
	COUNT {
		@Override
		public TaskTempletBase createNewTemplet() {
			return null;
		}
	},
	
	/**
	 * 道具检测类任务
	 */
	PROP {
		@Override
		public TaskTempletBase createNewTemplet() {
			return new PropTaskTemplet();
		}
	},
	
	/**
	 * 关卡任务
	 */
	MISSION{

		@Override
		public TaskTempletBase createNewTemplet() {
			return new MissionTaskTemplet();
		}
		
	},
	
	/**
	 *直接完成类的计数任务
	 */
	DIRECT_COUNT{

		@Override
		public TaskTempletBase createNewTemplet() {
			return new DirectCountTaskTemplet();
		}
		
	},
	
	/**
	 * 玩家自定义任务
	 */
	CUSTOM {
		@Override
		public TaskTempletBase createNewTemplet() {
			return null;
		}
	};

	 public abstract TaskTempletBase createNewTemplet();
}
