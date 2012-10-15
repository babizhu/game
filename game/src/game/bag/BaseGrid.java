package game.bag;

import game.prop.templet.BasePropTemplet;

public class BaseGrid implements IGrid {
	
	/**
	 * 道具的类型模板
	 */
	private	BasePropTemplet		templet;
	
	/**
	 * 关联的道具id，不存在设置为0
	 */
	private long				propId;
	
	/**
	 * 当前格子拥有的物品数量
	 */
	private	int					count;

	
	public BaseGrid(BasePropTemplet templet, long propId, int count) {
		super();
		this.templet = templet;
		this.propId = propId;
		this.count = count;
	}

	public BasePropTemplet getTemplet() {
		return templet;
	}

	public void setTemplet(BasePropTemplet templet) {
		this.templet = templet;
	}

	public long getPropId() {
		return propId;
	}

	public void setPropId(long propId) {
		this.propId = propId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	

}
