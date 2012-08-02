package util.timewheel1;



import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * ������һ������ʱ��hash��wheel��ʱ��
 * ����ʱ�䣺2011-9-16����11:44:02
 * @author yq76034150
 *
 */
public class HashedWheelTimer {
	volatile int tick = 1;
	volatile int currentWheel;
	private Map<TimeOut, Boolean>[] wheel;
	final int mask;
	private long delay;
	//private int ticksPerWheel;
	private long tickDuration;
	//private long roundDuration;
	
	/**
	 * ������
	 * @param ticksPerWheel һȦ����tick
	 */
	public HashedWheelTimer(int ticksPerWheel, long tickDuration, long delay){
		mask = ticksPerWheel - 1;
		this.delay = delay; 
		//this.ticksPerWheel = ticksPerWheel;
		this.tickDuration = tickDuration;
		//this.roundDuration = tickDuration * ticksPerWheel;
		createWheel(ticksPerWheel);
	}
	
	/**
	 * 
	 * ������wheel��������
	 * @param timeout
	 */
	public void newTimeOut(TimeOut timeout){
		long shift = delay / tickDuration;
		//long remainRounds = delay / roundDuration;
		int stopIndex = currentWheel + (int)shift & mask;
		
		wheel[stopIndex].put(timeout, true);
	}
	
	/**
	 * 
	 * �������ⲿ�̵߳��ã��ⲿ�̵߳��ü�������tickDurationһ��
	 */
	public void run(long start){
		//ģ���ⲿִ���߳�ִ�м����
		try {
			Thread.sleep(tickDuration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tick++;
		currentWheel = currentWheel + 1 & mask;
		long deadline = start + tick * tickDuration;
		
		Map<TimeOut, Boolean> map = wheel[currentWheel];
		
		TimeOut timeOut = null;
		for(Entry<TimeOut, Boolean> entry : map.entrySet()){
			timeOut = entry.getKey();
			if(timeOut.getDeadline() <= deadline){
				map.remove(timeOut);
				System.out.println("tick: " + tick + " delete: " + currentWheel + timeOut);
			}
		}

	}

	/**
	 * 
	 * ��������ʼ��wheel
	 * @param ticksPerWheel
	 */
	@SuppressWarnings("unchecked")
	private void createWheel(int ticksPerWheel) {
		wheel = new Map[ticksPerWheel];
		for(int i = 0; i < ticksPerWheel; i++){
			wheel[i] = new ConcurrentHashMap<TimeOut, Boolean>();
		}
	}

	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		long tickDuration = 1000;
		long delay = 9000;
		HashedWheelTimer timer = new HashedWheelTimer(4, tickDuration, delay);
		
		long start = System.currentTimeMillis();
		TimeOut timeout = new TimeOut(start, start + delay );
		timer.newTimeOut(timeout);
		timer.run(start);
		
		//4s�� ��֤��֮ǰ������һ�����
		for(int i = 0; i < 3; i++){
			timer.run(start);
		}

		long start2 = System.currentTimeMillis();
		TimeOut timeout2 = new TimeOut(start2, start2 + delay );
		timer.newTimeOut(timeout2);
		timer.run(start);
			
		//��ı�ͷ��ʱ�� queue�ṹ�ľͻ�����
		//timeout.setTime(start + 30000);
		//timeout.setDeadline(start + 30000 + delay);
		timer.newTimeOut(timeout);
		timer.run(start);
		
		for(int i = 0; i < 50; i++){
			timer.run(start);
		}
	}

}