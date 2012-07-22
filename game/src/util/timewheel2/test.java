package util.timewheel2;

import java.util.concurrent.TimeUnit;
class task1 implements ITimerTask{

	int i = 0;
	ITimer timer;
	public task1( ITimer timer ) {
		this.timer = timer; 
	}
@Override
	public void run ( ITimeout timeout ) throws Exception {
	
		//System.out.println( timeout. + ":" +getClass().getSimpleName() );
		if( i < 3 ){
			//timer.newTimeout( this, 1000 );
			i++;
		}
		
	}
	
}
public class test {

	public static void main(String[] args) throws InterruptedException {
		ITimer timer = new HashedWheelTimer();
		
//		ITimerTask task = new ITimerTask() {
//			
//			@Override
//			public void run(ITimeout timeout) throws Exception {
////				System.out.println( Thread.currentThread() + " 11 " + getClass().getSimpleName() );
////				int s = 9/0;
////				Thread.sleep( 5000 );
//				int i = 0;
//				i++;
//				
//			}
//		};
//		
//		ITimerTask task1 = new ITimerTask() {
//			
//			@Override
//			public void run(ITimeout timeout) throws Exception {
//				System.out.println( Thread.currentThread() + " 22 " + getClass().getSimpleName() );
//			}
//		};
//		
//		ITimeout to = timer.newTimeout(task , 1, TimeUnit.SECONDS );
//		Thread.sleep( 1100 );
//		ITimeout to1 = timer.newTimeout(task1 , 1, TimeUnit.SECONDS );
//		
	
		
		for( int i = 0; i < 1000000; i++ ){		
			timer.newTimeout( new task1(timer), 1, TimeUnit.SECONDS);
		
		}
		
		
		//wheelCursor + 1 & mask
	}
}
