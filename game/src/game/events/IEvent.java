package game.events;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;




public interface IEvent {

	public void run( UserInfo user, ByteBuffer buf ) throws IOException; 

	short getEventId ();
}
