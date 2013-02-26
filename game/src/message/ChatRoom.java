package message;

import game.events.Event;
import game.events.all.message.SendMsgEvent;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import user.UserInfo;
import util.UtilBase;

public class ChatRoom implements IChat{
	
	/**
	 * 
	 */
	private List<UserInfo> users = new CopyOnWriteArrayList<UserInfo>();

	/**
	 * 请注意控制str的长度，不要超过512 / 3 
	 */
	@Override
	public void sendToAll( UserInfo sender, String content ) {
		ByteBuffer msg = ByteBuffer.allocate( 512 );
		UtilBase.encodeString( msg, content );
		SendMsgEvent p = (SendMsgEvent) Event.SYSTEM_SEND_MSG.getEventInstance();
		for( UserInfo u : users ){
			p.run( u, msg.asReadOnlyBuffer() );
		}
	}

	@Override
	public void sendToOne(UserInfo sender, UserInfo receiver, String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendSystemMsg(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUser(UserInfo user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUser(UserInfo user) {
		// TODO Auto-generated method stub
		
	}
	
	

}
