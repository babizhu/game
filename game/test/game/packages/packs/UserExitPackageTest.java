package game.packages.packs;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.Test;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import util.ErrorCode;

import define.SystemCfg;

public class UserExitPackageTest extends BasePackageTest {

	@Test
	public void login() throws IOException, InterruptedException{
		int count = 1;
		for( int i = 0; i < count; i++ ){
//			test1();
//			test2();
//			watiForServerClose();
			duplicateLogin();
		}
	}
	
	/**
	 * 连上去，等几秒断开
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unused")
	private void test1() throws IOException, InterruptedException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		Thread.sleep( 1 );
		nbc.close();
	}
	
	/**
	 * 1、用错误的用户名登陆，然后断开
	 * 2、用正确的用户名登陆，然后断开
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unused")
	private void test2() throws IOException, InterruptedException{
		doLogin( "错误的用户名", true );
		doLogin( "刘昆0", true );
	}
	
	/**
	 * 主动等到服务器关闭连接
	 * 1、未登录的用户
	 * 2、登陆的用户
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unused")
	private void watiForServerClose() throws IOException, InterruptedException{
		//IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		//nbc.readByte();//block在这里
		//nbc.close();
	}
	
	/**
	 * 两个连接用同一个用户名登陆，第一个连接会被自动断掉
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	private void duplicateLogin() throws IOException, InterruptedException{
		doLogin( "刘昆0", false );
		doLogin( "刘昆0", false );
		//Thread.sleep( 5000 );
	}
	
	private void doLogin( String name, boolean isAutoClose ) throws IOException, InterruptedException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		ByteBuffer buf = new UserLoginPackageTest().sendPackage(nbc, name);
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		if( name.equals( "错误的用户名" ) ){
			assertEquals( ErrorCode.USER_NOT_FOUND, code );
		}
		else{
			assertEquals( ErrorCode.SUCCESS, code );
		}
		
		Thread.sleep( 1 );
		if( isAutoClose ){
			nbc.close();
		}
	}
	
	@Override
	public short getPacketNo() {
		return 0;
	}

}
