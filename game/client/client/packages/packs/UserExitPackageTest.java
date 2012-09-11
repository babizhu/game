package client.packages.packs;



import game.packages.Packages;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import user.UserInfo;
import util.BaseUtil;
import util.ErrorCode;
import define.SystemCfg;

/**
 * 测试游戏的退出机制，这里并没有真正的包
 * 1、利用多线程创建多个连接
 * 2、每个线程发送一个登陆包，然后等待一段时间（随机），然后主动切断连接
 * 
 * @author liukun
 * 2012-8-30 下午04:31:15
 */
public class UserExitPackageTest extends BasePackageTest {

	public UserExitPackageTest(int count) {
		super(count);
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
	public
	void parse( ByteBuffer buf ){
		UserInfo user = new UserInfo( null );
//		user.setStatus( UserStatus.fromNum( buf.get() ) );
		ErrorCode code = ErrorCode.values()[buf.getShort()]; 
	
		if( code == ErrorCode.SUCCESS ){//成功登陆
			user.setNickName( BaseUtil.decodeString(buf) );			//昵称
			user.setSex( buf.get() );								//性别
			user.setAdult( buf.get() == 1? true:false  );			//是否成年
			user.setStrength( buf.getShort()  );					//体力
			user.changeMoney( buf.getInt(), "parse" );							//金币
			user.setLoginCount( buf.getShort() );					//登陆次数
			user.setCreateTime( buf.getInt() );						//创建时间——秒数
		}
		int sleepMills = new Random().nextInt( 500 ) * 1000;
		try {
			Thread.sleep( sleepMills );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println( code + "\t" + Thread.currentThread().getName() + "睡眠时间" + sleepMills/1000 + "秒" );
	}
	public void run() throws IOException, InterruptedException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		/***************************************************发包参数***********************************************/
		String name = "刘昆";
		/***************************************************发包参数***********************************************/
		for( int i = 0; i < count; i++ ){
			System.out.print( i + ":");
			
			ByteBuffer buf = createContent( name + i );
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

		long begin = System.nanoTime();
		int count = 1;
		new UserExitPackageTest( count ).run();
		System.out.println( "用时" + (System.nanoTime() - begin) / 1000000000f + "秒");
		System.exit(0);
		
		
	}
	

}
