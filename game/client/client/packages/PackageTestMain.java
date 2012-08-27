package client.packages;


import client.packages.packs.BasePackageTest;
import client.packages.packs.UserLoginPackageTest;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import util.BaseUtil;


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
		int count = 30000;
		int threadCount = 5000;
		ExecutorService exec = Executors.newCachedThreadPool();
		long begin = System.nanoTime();
		for( int i = 0; i < threadCount; i++ ){
			BasePackageTest p = new UserLoginPackageTest( count );
			exec.execute( new Client( p ) );
		}
		exec.shutdown();
		exec.awaitTermination( 100, TimeUnit.DAYS );
		System.out.println( "用时" + (System.nanoTime() - begin) / BaseUtil.TO_SECOND );
		System.exit(0);
	}
}
