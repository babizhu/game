package game.packages.packs;


import static org.junit.Assert.assertEquals;
import game.packages.Packages;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import define.SystemCfg;

import util.BaseUtil;
import util.ErrorCode;

public class UserLoginPackageTest extends PackageTest {

	IBlockingConnection nbc;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
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
	 * 发送一个完整的登陆包
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	private ByteBuffer sendLoginPackage( IBlockingConnection nbc, String name ) throws IOException{
		this.nbc = nbc;
		ByteBuffer buf = createContent( name );
		sendPacket( nbc, buf );
		return getData( nbc );

	}
	/**
	 * @throws IOException 
	 * 测试同一个连接反复发送各种登陆包的情况<br>
	 * 进入正式版本之后，第二~五次发包可能不会收到任何消息，针对这个种错误，服务器可能不会返回任何信息，现在为了测试用例需要，暂时有返回信息<br>
	 * 具体情况请查看{@link core.GameMainLogic#packageProcess} 
	 */
	//@Test
	public void Login() throws IOException{
		
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		/****************************测试玩家不存在的情况**************************************/		
		String name = "刘昆";
		ByteBuffer buf = sendLoginPackage( nbc, name );
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		
		assertEquals( ErrorCode.USER_NOT_FOUND, code );
		
		/****************************测试玩家正常登陆**************************************/
		name = "刘昆0";
		buf = sendLoginPackage( nbc, name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
		
		/****************************测试玩家第二次发送登陆包**************************************/
		buf = sendLoginPackage( nbc, name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );
		
		/****************************测试玩家第三次发送登陆包**************************************/
		buf = sendLoginPackage( nbc, name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );
		
		/****************************测试玩家第四次发送登陆包，用另外的不存在的用户名****************/
		name = "另外一个不存在名字";
		buf = sendLoginPackage( nbc, name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );
		
		/****************************测试玩家第五次发送登陆包，用另外的已经存在的用户名***************/
		name = "刘昆1";
		buf = sendLoginPackage( nbc, name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );
	}
	
	/**
	 * 同一个用户用两个con两次登陆
	 * @throws IOException 
	 */
	@Test
	public void DuplicateLoginByTwoConn() throws IOException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		
		String name = "刘昆0";
		ByteBuffer buf = sendLoginPackage( nbc, name );
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
		
		IBlockingConnection nbc1 = new BlockingConnection( "localhost", SystemCfg.PORT );
		
		name = "刘昆0";
		ByteBuffer buf1 = sendLoginPackage( nbc1, name );
		ErrorCode code1 = ErrorCode.values()[buf1.getShort()];
		assertEquals( ErrorCode.SUCCESS, code1 );
	}
	

	/**
	 * 
	 * @param name	玩家名字
	 * @return
	 */
	public ByteBuffer createContent( String name ){
		ByteBuffer buf = createEmptyPackage(1024);
		BaseUtil.encodeString( buf, name );
		return buf;
	}

	@Override
	public short getPacketNo() {
		return Packages.USER_LOGIN.toNum();
	}
}
