/**
 * 
 */
package game.packages.packs;


import game.packages.Packages;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import util.ErrorCode;
import define.SystemCfg;

class DeadLockClient implements Runnable{

	DeadLockTestPackageTest deadPackage;
	String name;
	
	public DeadLockClient(DeadLockTestPackageTest deadPackage, String name ) {
		super();
		this.deadPackage = deadPackage;
		this.name = name;
	}

	@Override
	public void run() {
		UserLoginPackageTest t = new UserLoginPackageTest();
		IBlockingConnection nbc;
		try {
			nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
			ByteBuffer buf = t.sendPackage( nbc, name );//先登录
			ErrorCode code = ErrorCode.values()[buf.getShort()];
			if( code == ErrorCode.SUCCESS ){
				deadPackage.sendDeadLoclTestPackage( nbc );//发送导致死锁的包
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
/**
 * @author liukun
 * 2012-9-2
 */
public class DeadLockTestPackageTest extends BasePackageTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @param nbc
	 */
	public void sendDeadLoclTestPackage(IBlockingConnection nbc) {
		ByteBuffer buf = createEmptyPackage(1024);
		sendPacket( nbc, buf );
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 用两个线程分别代表"刘昆0"和"刘昆1"登陆服务器，并发送DeadLockTestPackage包，导致服务器死锁
	 * @throws InterruptedException 
	 */
	@Test
	public void testDeadLock() throws InterruptedException{
		
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute( new DeadLockClient( new DeadLockTestPackageTest(), "刘昆0") );
		exec.execute( new DeadLockClient( new DeadLockTestPackageTest(), "刘昆1") );
		exec.shutdown();
		exec.awaitTermination( 1000, TimeUnit.DAYS );
		
	}

	/* (non-Javadoc)
	 * @see game.packages.packs.PackageTest#getPacketNo()
	 */
	@Override
	public short getPacketNo() {
		return Packages.DEAD_LOCK_TEST.toNum();
	}
}
