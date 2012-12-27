package game.events.all;


import static org.junit.Assert.assertEquals;
import game.events.EventManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import org.junit.Test;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import util.BaseUtil;
import util.ErrorCode;
import define.SystemCfg;

public class UserCreateEventTest extends BaseEventTest{

	
	/**
	 * 发送一个完整的注册包
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	public ByteBuffer sendEvent( IBlockingConnection nbc, String name, String nickName, byte sex ) throws IOException{
		//this.nbc = nbc;
		ByteBuffer buf = createContent( name, nickName, sex );
		sendEvent( nbc, buf );
		return getData( nbc );
	}

	private ByteBuffer createContent(String name, String nickName, byte sex) {
		ByteBuffer buf = createEmptyPackage(1024);
		BaseUtil.encodeString( buf, name );
		BaseUtil.encodeString( buf, nickName );
		buf.put( sex );
		return buf;
	}

	@Override
	public short getEventId() {
		return EventManager.USER_CREATE.toNum();
	}
	
	@Test
	public void test1() throws IOException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		
		String name = "刘0" + new Random().nextInt( 1000000) + 1000000;//生成一个基本不容易重复的名字，方便测试
		ByteBuffer response = sendEvent( nbc, name, "nickname_" + name, (byte) 1 );
		ErrorCode code = ErrorCode.values()[response.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
		
		response = sendEvent( nbc, name, "nickname_" + name, (byte) 1 );
		code = ErrorCode.values()[response.getShort()];
		assertEquals( ErrorCode.USER_DUPLICATE_NAME, code );
		
		name = "刘0" + new Random().nextInt( 1000000) + 1000000;//生成一个基本不容易重复的名字，方便测试
		response = sendEvent( nbc, name, "巴比猪0", (byte) 1 );//测试昵称冲突
		code = ErrorCode.values()[response.getShort()];
		assertEquals( ErrorCode.USER_DUPLICATE_NAME, code );
	}

}
