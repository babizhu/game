package game.packages.packs;


import static org.junit.Assert.assertEquals;

import game.packages.Packages;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.Test;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import util.ErrorCode;
import define.SystemCfg;

public class TaskGetAllActivePackageTest extends BasePackageTest  {

	private ByteBuffer sendPackage( IBlockingConnection nbc ) throws IOException{
		//this.nbc = nbc;
		ByteBuffer buf = createContent(  );
		sendPacket( nbc, buf );
		return getData( nbc );
	}
	
	private ByteBuffer createContent() {
		ByteBuffer buf = createEmptyPackage(1024);
		return buf;
	}

	@Test
	public void test() throws IOException, InterruptedException{
		IBlockingConnection nbc = new BlockingConnection( "localhost", SystemCfg.PORT );
		String name = "bbz";
	
		ByteBuffer buf = new UserLoginPackageTest().sendPackage( nbc, name );
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
		for( int i = 0; i < 200000; i++ )
		{
			buf = sendPackage(nbc);
			byte size = buf.get();
			assertEquals( 5, size );
		}
		
		//Thread.sleep( 2000 );
	}

	@Override
	public short getPacketNo() {
		// TODO Auto-generated method stub
		return Packages.TASK_GET_ALL_ACTIVE.toNum();
	}

}
