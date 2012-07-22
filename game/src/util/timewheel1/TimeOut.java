package util.timewheel1;



/**
 * 
 * 
 * ���������ӹ���
 * ����ʱ�䣺2011-9-16����08:42:34
 * @author yq76034150
 *
 */
public class TimeOut {
	final long time;
	final long deadline;
	private int rounds;
	
	//���ӵ��û�id
	//���ӵ�channel
	
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