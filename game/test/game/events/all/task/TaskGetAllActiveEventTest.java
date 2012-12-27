package game.events.all.task;


import static org.junit.Assert.assertEquals;

import game.events.EventManager;
import game.events.all.BaseEventTest;
import game.events.all.UserLoginEventTest;
import game.task.BaseTask;
import game.task.cfg.TaskTempletCfg;
import game.task.enums.TaskStatus;

import java.io.IOException;
import java.nio.ByteBuffer;


import org.junit.BeforeClass;
import org.junit.Test;
import org.xsocket.connection.BlockingConnection;
import org.xsocket.connection.IBlockingConnection;

import util.BaseUtil;
import util.ErrorCode;
import define.SystemCfg;

public class TaskGetAllActiveEventTest extends BaseEventTest  {

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		TaskTempletCfg.init();
	}
	
	private ByteBuffer sendPackage( IBlockingConnection nbc ) throws IOException{
		ByteBuffer buf = createContent(  );
		sendEvent( nbc, buf );
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
	
		ByteBuffer buf = new UserLoginEventTest().sendPackage( nbc, name );
		ErrorCode code = ErrorCode.values()[buf.getShort()];
		assertEquals( ErrorCode.SUCCESS, code );
		int count = 1000;
		for( int i = 0; i < count; i++ )
		{
			buf = sendPackage( nbc );

			byte size = buf.get();
			assertEquals( 5, size );
			for( int t = 0; t < size; t++ ){
				short templetId = buf.getShort();
				BaseTask task = TaskTempletCfg.getTempletById(templetId).createTask();
				
				task.setStatus( TaskStatus.fromNum( buf.get() ) );
				task.parseParamFromStr( BaseUtil.decodeString(buf) );
				System.out.println( task );
			}
			System.out.println( i );
		}
	}

	@Override
	public short getEventId() {
		return EventManager.TASK_GET_ALL_ACTIVE.toNum();
	}

}
