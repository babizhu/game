package user;

public enum UserStatus {
	/**
	 * 访客状态
	 * 也是玩家连接上来之后的最初状态
	 */
	GUEST,
	/**
	 * 新玩家状态
	 */
	NEW,
	/**
	 * 禁止登陆状态
	 */
	BAN,
	/**
	 * 正常登陆状态
	 */	
	LOGIN;//

}
