package util.timewheel1;



/**
 * 
 * 
 * 描述：连接管理
 * 创建时间：2011-9-16下午08:42:34
 * @author yq76034150
 *
 */
public class TimeOut {
	final long time;
	final long deadline;
	private int rounds;
	
	//连接的用户id
	//连接的channel
	
	public TimeOut(long time, long deadline) {
		super();
		this.time = time;
		this.deadline = deadline;
	}

	public int getRounds() {
		return rounds;
	}

	public void setRounds(int rounds) {
		this.rounds = rounds;
	}

	public long getTime() {
		return time;
	}

	public long getDeadline() {
		return deadline;
	}
	
	public String toString(){
		return "survival: " + (System.currentTimeMillis() - time);
	}
}