package game.events.all.base;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import user.UserInfo;
import game.award.AwardInfo;
import game.events.BaseEvent;
import game.events.EventDescrip;

/**
 * 告知玩家获得了什么奖励，通常是由服务器主动发出
 * @author liukun
 *
 */

@EventDescrip(desc = "告知玩家获得了什么奖励，通常是由服务器主动发出")
public class SendAwardEvent extends BaseEvent{

	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public void run( UserInfo user, List<AwardInfo> awards ) throws IOException{
		ByteBuffer buffer = buildEmptyPackage( 1024 );
		for( AwardInfo award : awards ){
			award.buildBytes(buffer);
		}
		sendPackage( user.getCon(), buffer );
	}

}
