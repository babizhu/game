package game.bag;

import com.alibaba.druid.mock.MockArray;

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

	/**
	 * 格子是否被修改过，如果修改过则此格子的信息有必要发送至客户端同步信息
	 */
	private boolean				isModify = false;
	
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

	public boolean isModify() {
		return isModify;
	}

	public void setModify(boolean isModify) {
		this.isModify = isModify;
	}

	/**
	 * 扣除此格子中的道具
	 * 如果要扣除的道具数量比实际情况多，则扣到0为止
	 * @param needCount
	 * @return
	 */
	public int remove( int needCount ) {
		int realCount = count >= needCount ? needCount : count;
		count -= needCount;
		isModify = true;
		return realCount;
	}
	
	

}
