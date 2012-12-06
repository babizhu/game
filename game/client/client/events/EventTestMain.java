package client.events;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import client.events.event.BaseEventTest;
import client.events.event.CUserLoginEventTest;

/**
 * 发包线程
 * @author admin
 * 2012-8-28 上午10:09:10
 */
class Client implements Runnable{

	BaseEventTest pack;
	public Client( BaseEventTest pack ){
		this.pack = pack;
	}
	
	@Override
	public void run() {
		try {
			pack.run();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
public class EventTestMain {
	public static void main(String[] args) throws InterruptedException {
		int count = 10000;//发包数量
		int threadCount = 20;//线程数量
		ExecutorService exec = Executors.newCachedThreadPool();
		long begin = System.nanoTime();
		for( int i = 0; i < threadCount; i++ ){
			BaseEventTest p = new CUserLoginEventTest( count );
			exec.execute( new Client( p ) );
		}
		exec.shutdown();
		exec.awaitTermination( 100, TimeUnit.DAYS );
		
		System.out.println("用时" + (System.nanoTime() - begin) / 1000000000f + "秒");
		System.exit(0);
	}
}
