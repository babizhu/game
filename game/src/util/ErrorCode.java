package util;

public enum ErrorCode {

	SUCCESS("操作成功"),
	/*****************************************玩家相关操作*********************************/
	USER_LEVEL_NOT_ENOUGH( "玩家等级不足" ),	
	USER_HAS_LOGIN( "玩家已经登陆了" ),	
	USER_DUPLICATE_NAME( "玩家用户名重复" ),	
	USER_NOT_FOUND( "玩家尚未注册" ),	
	USER_INVALID_LOGIN( "登录信息有误" ),	
	USER_NOT_LOGIN( "玩家尚未登陆" ),	
	USER_HAS_BAN( "玩家被ban，无法登陆" ),	
	USER_GOLD_NOT_ENOUTH( "玩家金币不足" ),	
	USER_CASH_NOT_ENOUTH( "现金不足" ),
	
	/*****************************************任务相关操作*********************************/
	TASK_HAS_ACCEPTED( "无法重复接同一个任务"),	
	TASK_NOT_FOUND( "任务没找到" ),
	
	/*****************************************通信包相关操作********************************/
	PACKAGE_NOT_FOUND( "通信包未找到" ),	
	PACKAGE_SAFE_CHECK_FAIL( "包的安全检测失败，用户短时间内发送大量相同的数据包到服务器端" ),
	
	/*****************************************道具相关操作*********************************/
	PROP_NOT_ENOUGH( "道具数量不足" ),
	PROP_NOT_FOUNTD( "道具不存在" ),
	
	/*****************************************战士相关操作*********************************/
	FIGHTER_LEVEL_NOT_ENOUGH( "战士等级不足" ),
	
	/*****************************************背包相关操作*********************************/
	BAG_IS_FULL( "空闲格子不足" ),
	
	/*****************************************商城相关操作*********************************/
	SHOP_CANT_BUY( "此商品不能从商店中购买" ),
	
	/*****************************************获取奖品相关操作*********************************/
	AWARD_NOT_FOUND( "奖品不存在"),
	
	
	
	DB_ERROR("数据库错误"),	

	UNKNOW_ERROR("未知错误");

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

