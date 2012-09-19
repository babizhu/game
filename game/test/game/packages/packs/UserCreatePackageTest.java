package game.packages.packs;


import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import game.packages.Packages;

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

public class UserCreatePackageTest extends BasePackageTest{

	
	/**
	 * 发送一个完整的登陆包
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	public ByteBuffer sendPackage( IBlockingConnection nbc, String name, String nickName, byte sex ) throws IOException{
		//this.nbc = nbc;
		ByteBuffer buf = createContent( name, nickName, sex );
		sendPacket( nbc, buf );
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
	public short getPacketNo() {
		return Packages.USER_CREATE.toNum();
	}
	
	@Test
	public void test1() throws IOException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		
		String name = "刘0" + new Random().nextInt( 1000000) + 1000000;//生成一个基本不容易重复的名字，方便测试
		ByteBuffer buf = sendPackage( nbc, name, "nickname_" + name, (byte) 1 );
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
	}

}