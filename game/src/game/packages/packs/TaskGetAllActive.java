/**
 * 
 */
package game.packages.packs;

import game.packages.BasePackage;
import game.task.BaseTask;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

import user.UserInfo;
import util.BaseUtil;

/**
 * 获取玩家的所有活动任务
 * @author liukun
 * 2012-9-25
 */
public class TaskGetAllActive  extends BasePackage {

	/* (non-Javadoc)
	 * @see game.packages.BasePackage#run(user.UserInfo, java.nio.ByteBuffer)
	 */
	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		Map<Short,BaseTask> activeTasks = user.getTaskManager().getAllActive();
		ByteBuffer buffer = buildEmptyPackage( 1024 );
		for( BaseTask t : activeTasks.values() ){
			buffer.putShort( t.getTemplet().getTempletId() );
			buffer.put( t.getStatus().toNum() );
			//buffer.putInt( (Integer) t.getParam() );
			BaseUtil.encodeString( buffer, t.getParam().toString() );
						
		}
		
		sendPackage( user.getCon(), buffer );
		
	}

}
