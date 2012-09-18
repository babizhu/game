package client.packages;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import util.BaseUtil;
import client.packages.packs.BasePackageTest;
import client.packages.packs.CUserLoginPackageTest;

/**
 * 发包线程
 * @author admin
 * 2012-8-28 上午10:09:10
 */
class Client implements Runnable{

	BasePackageTest pack;
	public Client( BasePackageTest pack ){
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
public class PackageTestMain {
	public static void main(String[] args) throws InterruptedException {
		int count = 1;//发包数量
		int threadCount = 2;//线程数量
		ExecutorService exec = Executors.newCachedThreadPool();
		long begin = System.nanoTime();
		for( int i = 0; i < threadCount; i++ ){
			BasePackageTest p = new CUserLoginPackageTest( count );
			exec.execute( new Client( p ) );
		}
		exec.shutdown();
		exec.awaitTermination( 100, TimeUnit.DAYS );
		System.out.println( "用时" + (System.nanoTime() - begin) / BaseUtil.TO_SECOND );
		System.exit(0);
	}
}
