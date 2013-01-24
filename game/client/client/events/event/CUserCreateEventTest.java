package client.events.event;



import game.events.EventManager;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import util.BaseUtil;
import util.ErrorCode;
import define.SystemCfg;

public class CUserCreateEventTest extends BaseEventTest {

	public CUserCreateEventTest(int count) {
		super(count);
	}
	
	/**
	 * 
	 * @param name	玩家名字
	 * @param name	玩家昵称
	 * @return
	 */
	public ByteBuffer createContent( String name, String nickName ){
		ByteBuffer buf = createEmptyEvent( 1024 );
		BaseUtil.encodeString( buf, name );					//用户名
		BaseUtil.encodeString( buf, nickName );				//昵称
		buf.put( (byte) 1 );								//性别
		return buf;
	}
	
	@Override
	public
	void parse( ByteBuffer buf ){
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		System.out.println( code );
	}
	public void run() throws IOException, InterruptedException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		for( int i = 0; i < count; i++ ){
			System.out.print( i + ":");
			String name = "刘昆" + i;
			String nickName = "巴比猪" + i;
			ByteBuffer buf = createContent( name, nickName );
			sendPacket( nbc, buf );
			ByteBuffer data = getData( nbc );
			parse( data );
		}
		nbc.close();
	}

	@Override
	public short getEventId() {
		return EventManager.USER_CREATE.toNum();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
//		for( int i = 0; i < 1000; i++ ){
//			System.out.print( i + ":");
//			UserLoginPackageTest lt = new UserLoginPackageTest();
//			lt.run();
//		}
		int runCount = 10000;
		long begin = System.nanoTime();
		new CUserCreateEventTest( runCount ).run();
		System.out.println( "用时" + ((System.nanoTime() - begin) / 1000000000f) + "秒" );
		
		System.exit(0);
	}
	

}
