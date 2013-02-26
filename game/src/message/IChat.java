package message;

import user.UserInfo;

public interface IChat {
	
	void sendToAll( UserInfo sender, String str );
	void sendToOne( UserInfo sender, UserInfo receiver, String str );
	
	/**
	 * 传送系统消息
	 * @param str
	 */
	void sendSystemMsg( String str );
	
	void addUser( UserInfo user );
	
	void removeUser( UserInfo user );

}
