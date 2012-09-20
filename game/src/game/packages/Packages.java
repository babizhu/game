package game.packages;


import game.packages.packs.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import user.UserInfo;

/**
 * 100以内为系统保留
 * 200以内为系统功能需求
 * 500以上为游戏需要
 * 
 * 30000以上为测试用(暂时)
 * 
 * @注意：为了传输方便，枚举对应的数字不得超过Short.MAX_VALUE
 * @author liukun
 * 2012-8-25
 */
public enum Packages {
	

	SYSTEM_SEND_ERROR_CODE( 100, new SystemSendErrorCodePackage() ),
	USER_LOGIN( 201, new UserLoginPackage() ),
	USER_CREATE( 202, new UserCreatePackage() ),
	USER_EXIT( 203, new UserExitPackage() ), 
	EQUIPMENT_LEVEL_UP( 501, new EquipmentLevelUpPackage() ),
	DEAD_LOCK_TEST( 30000, new DeadLockTestPackage() ); 
				
	//private final static Logger 			logger = LoggerFactory.getLogger( PackageDefine.class ); 
	
	private final short 			number;
	private final BasePackage 		packageInstance;
	
	Packages( int value, BasePackage packageInstance ) {
		
		this.number =  (short) value;
		this.packageInstance = packageInstance;
		this.packageInstance.setPackageNo( number );
	}
	private static final Map<Short, Packages> numToEnum = new HashMap<Short, Packages>();
	
	static{
		for( Packages a : values() ){
			
			Packages p = numToEnum.put( a.number, a );
			if( p != null ){
				throw new RuntimeException( a.number + "重复了" );
			}
		}
	}
	
	public BasePackage getPackageInstance() {
		return packageInstance;
	}
	public short toNum() {
		return number;
	}
	public static Packages fromNum( short n ){
		return numToEnum.get( n );
	}
	
	/**
	 * 运行此枚举所对应的包的run方法
	 * @param user
	 * @param buf
	 * @throws IOException 
	 */
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		packageInstance.run( user, buf );
	}
	
	/**
	 * 打印所有的包信息
	 * @param args
	 */
	public static void main(String[] args) {
		Formatter f = new Formatter(System.out);
		f.format("%-15s %-127s %-70s \n", "包号", "类别", "功能说明");
		f.format("%-15s %-127s %-70s \n", "－－", "－－", "－－－－");
		for( Packages p : Packages.values() ){
			
			Class<?> c = p.packageInstance.getClass();
			PacketDescrip desc = c.getAnnotation(PacketDescrip.class);
			String s = null;
			s = (desc == null) ? "" : desc.desc();
			String className = c.getName().substring( c.getName().lastIndexOf(".") + 1 );
			f.format("%-8s %-50s %-70s \n", p.packageInstance.getPackageNo(), className, s );
		}
	}
}
