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

public class UserLoginPackageTest extends BasePackageTest {

	//IBlockingConnection nbc;
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
	public ByteBuffer sendPackage( IBlockingConnection nbc, String name ) throws IOException{
		//this.nbc = nbc;
		ByteBuffer buf = createContent( name );
		sendPacket( nbc, buf );
		return getData( nbc );
	}
	
	/**
	 *  客户端登陆之后发送三个包到服务器，在没有处理完第一个 包的时候（通过sleep），这里测试的是Packages.EQUIPMENT_LEVEL_UP<br>
	 *  客户端在用另外一个连接发送相同的登陆包导致第一个登陆掉线，此时来测试三个包的执行情况
	 *  情况1：
	 *  	正在执行第一个包的时候，最后一个登陆包进来了（把最后一个登陆包调整到前面来），另外两个EQUIPMENT_LEVEL_UP包不知道能否提交到服务器端，结论：不能
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test

	public void concurrentLogin() throws IOException, InterruptedException{
		
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		String name = "刘昆0";
		ByteBuffer buf = sendPackage( nbc, name );
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
		
		new EquipmentLevelUpPackageTest().sendPackage(nbc, 0, (byte) 0);//暂时不用取得回应
		new EquipmentLevelUpPackageTest().sendPackage(nbc, 0, (byte) 0);//暂时不用取得回应
		new EquipmentLevelUpPackageTest().sendPackage(nbc, 0, (byte) 0);//暂时不用取得回应

		/**
		 * 用另外一个con登陆
		 */
		IBlockingConnection nbc1 = new BlockingConnection( "localhost", SystemCfg.PORT );

		
		ByteBuffer buf1 = sendPackage( nbc1, name );
		code = ErrorCode.values()[buf1.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );
		
		
		System.out.println( "发包完成！" );
		
		nbc.close();
		nbc1.close();
		Thread.sleep( 2000 );
		
	}
	/**
	 * @throws IOException 
	 * 测试同一个连接反复发送各种登陆包的情况<br>
	 * 进入正式版本之后，第二~五次发包可能不会收到任何消息，针对这个种错误，服务器可能不会返回任何信息，现在为了测试用例需要，暂时有返回信息<br>
	 * 具体情况请查看{@link core.GameMainLogic#packageProcess} 
	 * @throws InterruptedException 
	 */
	@Test
	public void login() throws IOException, InterruptedException{
		
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		/****************************测试玩家不存在的情况**************************************/		
		String name = "不存在的人";
		ByteBuffer buf = sendPackage( nbc, name );
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		
		assertEquals( ErrorCode.USER_NOT_FOUND, code );
		
		/****************************测试玩家正常登陆**************************************/
		name = "刘昆0";
		buf = sendPackage( nbc, name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
		
		/****************************测试玩家第二次发送登陆包**************************************/
		buf = sendPackage( nbc, name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );
		
		
		
		/****************************测试玩家第三次发送登陆包**************************************/
		buf = sendPackage( nbc, name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );
		
		/****************************测试玩家第四次发送登陆包，用另外的不存在的用户名****************/
		name = "另外一个不存在名字";
		buf = sendPackage( nbc, name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );
		
		/****************************测试玩家第五次发送登陆包，用另外的已经存在的用户名***************/
		name = "刘昆1";
		buf = sendPackage( nbc, name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code );

		nbc.close();
	}
	
	/**
	 * 同一个用户用两个con两次登陆
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test
	public void duplicateLoginByTwoConn() throws IOException, InterruptedException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		
		String name = "刘昆0";
		ByteBuffer buf = sendPackage( nbc, name );
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
		
		IBlockingConnection nbc1 = new BlockingConnection( "localhost", SystemCfg.PORT );
		
		name = "刘昆0";
		ByteBuffer buf1 = sendPackage( nbc1, name );
		ErrorCode code1 = ErrorCode.values()[buf1.getShort()];
		assertEquals( ErrorCode.USER_HAS_LOGIN, code1 );
		
		Thread.sleep( 500 );//休息500ms
		assertEquals( false, nbc.isOpen() );//原有连接已经关闭
		
		buf = sendPackage( nbc1, name );
		code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );//再次登录成功
		
		nbc1.close();
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
//		return -1;
		return Packages.USER_LOGIN.toNum();
	}
}
