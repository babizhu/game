package util;

public enum ErrorCode {

	SUCCESS,
	LEVEL_NOT_ENOUGH,
	
	/**
	 * 玩家用户名重复
	 */
	USER_DUPLICATE_NAME,
	
	/**
	 * 玩家尚未注册
	 */
	USER_NOT_FOUND,
	
	/**
	 * 错误的登录信息
	 */
	USER_INVALID_LOGIN,
	
	/**
	 * 用户尚未登录
	 */
	USER_NOT_LOGIN,
	
	/**
	 * 任务没找到
	 */
	TASK_NOT_FOUND,
	
	/**
	 * 通信包未找到
	 */
	PACKAGE_NOT_FOUND,
	
	/**
	 * 包的安全检测失败，用户短时间内发送大量相同的数据包到服务器端
	 */
	PACKAGE_SAFE_CHECK_FAIL,
	
	UNKNOW_ERROR;
}
