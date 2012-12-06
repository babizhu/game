package client.events.event;



import game.events.EventManager;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import user.UserInfo;
import util.BaseUtil;
import util.ErrorCode;
import define.SystemCfg;

public class CUserLoginEventTest extends BaseEventTest {

	public CUserLoginEventTest(int count) {
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
		UserInfo user = new UserInfo( null, "liukun" );
//		user.setStatus( UserStatus.fromNum( buf.get() ) );
		ErrorCode code = ErrorCode.values()[buf.getShort()]; 
	
		if( code == ErrorCode.SUCCESS ){//成功登陆
			user.setNickName( BaseUtil.decodeString(buf) );					//昵称
			user.setSex( buf.get() );										//性别
			user.setAdult( buf.get() == 1? true:false  );					//是否成年
			user.setStrength( buf.getShort()  );							//体力
			user.changeCash( buf.getInt(), "CUserLoginPackageTest.parse" );	//金币
			user.setLoginCount( buf.getShort() );							//登陆次数
			user.setCreateTime( buf.getInt() );								//创建时间——秒数
		}
		System.out.println( code + "\t" + user );
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
	public short getEventId() {
		return EventManager.USER_LOGIN.toNum();
	}
	
	/**
	 * 发送一个登陆包，并保持住连接用于测试同一个用户名反复登录
	 * @throws IOException 
	 */
	void test() throws IOException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		/***************************************************发包参数***********************************************/
		String name = "刘昆0";
		/***************************************************发包参数***********************************************/
		ByteBuffer buf = createContent( name );
		sendPacket( nbc, buf );
		ByteBuffer data = getData( nbc );
		parse( data );
		
//		nbc.close();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {

//		long begin = System.nanoTime();
//		int count = 10000;
//		new UserLoginPackageTest( count ).run();
//		System.out.println( "用时" + (System.nanoTime() - begin) / 1000000000f + "秒");
//		System.exit(0);
		
		new CUserLoginEventTest(1).test();

		Thread.sleep(50000);
		
	}
	

}
