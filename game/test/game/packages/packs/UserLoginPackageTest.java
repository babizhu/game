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

import util.BaseUtil;
import util.ErrorCode;
import define.SystemCfg;

public class UserLoginPackageTest extends PackageTest {

	static IBlockingConnection nbc;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
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
	private ByteBuffer sendLoginPackage( String name ) throws IOException{
					
		ByteBuffer buf = createContent( name );
		sendPacket( nbc, buf );
		return getData( nbc );

	}
	/**
	 * @throws IOException 
	 */
	@Test
	public void Login() throws IOException{
		/****************************测试玩家不存在的情况**************************************/		
		String name = "刘昆";
		ByteBuffer buf = sendLoginPackage( name );
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		
		assertEquals( ErrorCode.USER_NOT_FOUND, code );
		
		/****************************测试玩家正常登陆**************************************/
		name = "刘昆0";
		buf = sendLoginPackage( name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
		
		/****************************测试玩家第二次发送登陆包**************************************/
		buf = sendLoginPackage( name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );
		
		/****************************测试玩家第三次发送登陆包**************************************/
		buf = sendLoginPackage( name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );
		
		/****************************测试玩家第四次发送登陆包，用另外的用户名************************/
		name = "另外一个名字";
		buf = sendLoginPackage( name );
		code = ErrorCode.values()[buf.getShort()];
		System.out.println( code );
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );
		
		
	}
	
	/**
	 * 同一个用户用两个con两次登陆
	 */
	@Test
	public void DuplicateLoginByTwoConn(){
		
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
