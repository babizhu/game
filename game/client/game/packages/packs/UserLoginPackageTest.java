package game.packages.packs;


import game.packages.Packages;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import util.BaseUtil;
import define.SystemCfg;

public class UserLoginPackageTest extends BasePackageTest {

	public UserLoginPackageTest(int count) {
		super(count);
		// TODO Auto-generated constructor stub
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * 
	 * @param name	玩家名字
	 * @return
	 */
	@Override
	public ByteBuffer createContent( String name ){
		ByteBuffer buf = createEmptyPackage(1024);
		BaseUtil.encodeString( buf, name );
		return buf;
	}
	
	@Override
	public
	void parse( ByteBuffer buf ){
		String name = BaseUtil.decodeString(buf);
		System.out.println( name );
	}
	public void run() throws IOException, InterruptedException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		for( int i = 0; i < count; i++ ){
			System.out.print( i + ":");
			String name = "liukun";
			ByteBuffer buf = createContent( name );
			sendPacket( nbc, buf );
			ByteBuffer data = getData( nbc );
			parse( data );
		}
		nbc.close();
	}

	@Override
	public short getPacketNo() {
		return Packages.USER_LOGIN.toNum();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
//		for( int i = 0; i < 1000; i++ ){
//			System.out.print( i + ":");
//			UserLoginPackageTest lt = new UserLoginPackageTest();
//			lt.run();
//		}
		new UserLoginPackageTest( 100 ).run();
		System.exit(0);
	}
	

}
