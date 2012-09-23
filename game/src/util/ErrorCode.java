package util;


public enum ErrorCode {

	@ErrorCodeDescrip(desc = "操作成功")
	SUCCESS,
	
	@ErrorCodeDescrip(desc = "等级不足")
	LEVEL_NOT_ENOUGH,
	
	@ErrorCodeDescrip(desc = "用户已经登陆了")
	USER_HAS_LOGIN,
	
	@ErrorCodeDescrip(desc = "玩家用户名重复")
	USER_DUPLICATE_NAME,
	
	@ErrorCodeDescrip(desc = "玩家尚未注册")
	USER_NOT_FOUND,
	
	@ErrorCodeDescrip(desc = "登录信息有误")
	USER_INVALID_LOGIN,
	
	@ErrorCodeDescrip(desc = "玩家尚未登陆")
	USER_NOT_LOGIN,
	
	@ErrorCodeDescrip(desc = "玩家被ban，无法登陆")
	USER_HAS_BAN,
	
	@ErrorCodeDescrip(desc = "无法重复接同一个任务")
	TASK_HAS_ACCEPT,
	
	@ErrorCodeDescrip(desc = "任务没找到")
	TASK_NOT_FOUND,
	
	@ErrorCodeDescrip(desc = "通信包未找到")
	PACKAGE_NOT_FOUND,
	
	@ErrorCodeDescrip(desc = "包的安全检测失败，用户短时间内发送大量相同的数据包到服务器端")
	PACKAGE_SAFE_CHECK_FAIL,
	
	@ErrorCodeDescrip(desc = "数据库错误")
	DB_ERROR,
	
	@ErrorCodeDescrip(desc = "未知错误")
	UNKNOW_ERROR ;
}
