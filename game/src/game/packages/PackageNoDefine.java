package game.packages;

import java.util.HashMap;
import java.util.Map;

public enum PackageNoDefine {

	USER_LOGIN(1),
	USER_EXIT(2);
private short number;
	
PackageNoDefine( int value ) {
		this.number = (byte) value;
	}
	private static final Map<Short, PackageNoDefine> numToEnum = new HashMap<Short, PackageNoDefine>();
	static{
		for( PackageNoDefine a : values() ){
			numToEnum.put( a.number, a );
		}
	}
	public short toNum() {
		return number;
	}
	static PackageNoDefine fromNum( short n ){
		return numToEnum.get( n );
	}
}
