package util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ͨ��һ���������߳��ṩϵͳʱ�䣬����ϵͳ����
 * @author admin
 *
 */
public class SystemTimer {
	private static final ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
	private SystemTimer(){};
	private volatile static long time = System.currentTimeMillis();
	private final static int TICK_UNIT = 50;
	
	private static class TimerTicker implements Runnable{

		@Override
		public void run () {
			time = System.currentTimeMillis();
			
		}
		
	}
	
	static{
		s.scheduleAtFixedRate( new TimerTicker(), TICK_UNIT, TICK_UNIT, TimeUnit.MILLISECONDS );
		Runtime.getRuntime().addShutdownHook( new Thread(){
			@Override
			public void run(){
//				System.out.println( "system will down");
				s.shutdown();
				
			}
		} );
	}
	
	public static long currentTimeMillis() {
		//System.out.println( "time:" + time );
		return time;
		//return System.currentTimeMillis();
	}
	
	public static void main ( String[] args ) throws InterruptedException {
		for( int i = 0; i < 10; i++ ){
			System.out.println( currentTimeMillis() );
			Thread.sleep( 1000 );
		}
	}
}
