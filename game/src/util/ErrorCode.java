package util;

public enum ErrorCode {

	SUCCESS,
	LEVEL_NOT_ENOUGH,
	/**
	 * 任务没找到
	 */
	TASK_NOT_FOUND,
	
	/**
	 * 通信包未找到
	 */
	PACAKAGE_NOT_FOUND,
	/**
	 * 包的安全检测失败，用户短时间内发送大量相同的数据包到服务器端
	 */
	PACKAGE_SECURITY_CHECK_FAIL,
	
	UNKNOW_ERROR, ;
}
