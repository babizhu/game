package game.events.all.prop;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;
import game.events.EventBase;


/**
 * 装备升级
 * @author liukun
 * 2012-9-14 下午03:12:14
 */
public class EquipmentLevelUpEvent  extends EventBase {
	
	private static final int PACK_LEN = 1024;
	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		//System.err.println( user.getCon().getId() + " " + this.getClass());
//		try {
//			//Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//System.err.println( user.getCon().getId() + " " + this.getClass() + " end " + System.nanoTime() );
		ByteBuffer response = buildEmptyPackage( PACK_LEN );
		synchronized (user) {
			
			sendPackage( user.getCon(), response );
		}
	}
}
