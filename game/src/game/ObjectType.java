package game;

import java.util.HashMap;
import java.util.Map;

import user.UserInfo;


/**
 * ���ֽ�������
 * @author admin
 *
 */
public enum ObjectType {

	/**
	 * ���
	 */
	MONEY(0),
	
	/**
	 * ������������ҵ�ս��ʱ��
	 */
	STRENGTH(1); 
	
	

	private final int number;
	ObjectType( int n ) {
		number = n;
	}
	
	private static final Map<Integer, ObjectType> intToEnum = new HashMap<Integer, ObjectType>();
	static{
		for( ObjectType a : values() ){
			intToEnum.put( a.number, a );
		}
	}
	public static ObjectType fromInt( int n ){
		return intToEnum.get( n );
	}
	public int toInt() {
		return number;
	}
	
	
	public static void main ( String[] args ) {
		ObjectType at = ObjectType.MONEY;
		System.out.println( at );
		ObjectType at1 = ObjectType.valueOf( "STRENGTH" );
		System.out.println( at1.toInt() );
		
		UserInfo u = new UserInfo();
		UserInfo u1 = u;
		System.out.println( u1 );
		u = null;
	
		System.out.println( u1 );
	}
}
