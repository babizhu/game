package util;

public enum ErrorCode {

	SUCCESS("操作成功"),
	
	LEVEL_NOT_ENOUGH("等级不足"),
	
	USER_HAS_LOGIN("用户已经登陆了"),
	
	USER_DUPLICATE_NAME("玩家用户名重复"),
	
	USER_NOT_FOUND("玩家尚未注册"),
	
	USER_INVALID_LOGIN("登录信息有误"),
	
	USER_NOT_LOGIN("玩家尚未登陆"),
	
	USER_HAS_BAN("玩家被ban，无法登陆"),
	
	TASK_HAS_ACCEPT("无法重复接同一个任务"),
	
	TASK_NOT_FOUND("任务没找到"),
	
	PACKAGE_NOT_FOUND("通信包未找到"),
	
	PACKAGE_SAFE_CHECK_FAIL("包的安全检测失败，用户短时间内发送大量相同的数据包到服务器端"),
	
	PROP_NOT_ENOUGH("道具数量不足"),
	PROP_NOT_FOUNTD("道具不存在"),
	
	FIGHTER_LEVEL_NOT_ENOUGH("战士等级不足"),
	
	GRID_NOT_ENOUGH( "空闲格子不足"),
	
	DB_ERROR("数据库错误"),
	
	UNKNOW_ERROR("未知错误") ;

	private String desc;
	ErrorCode( String desc ) {
		this.desc = desc;
	}
	public static void main(String[] args) {
		for( ErrorCode code : values() ){
			System.out.println( code + ":" + code.desc );
		}
		
	}
}

