package user;

import game.ObjectType;

import java.nio.ByteBuffer;

import org.slf4j.Logger; 

import org.slf4j.LoggerFactory; 



//import org.slf4j.; 



public class UserInfo {
	private final static Logger logger = LoggerFactory.getLogger( UserInfo.class ); 


	/**
	 * ���
	 */
	int			money;
	
	/**
	 * ����
	 */
	int			strength;
	
	/**
	 * �ǳ�
	 */
	String		nickName;
	/**
	 * �û���
	 */
	String		name;
	
	
	public int addStrength( int strengthAdd, String funcName ){
		strength += strengthAdd;
		buildLog( ObjectType.STRENGTH, strengthAdd, strength, funcName );
		return strength;
	}
	public int reduceStrength( int strengthReduce, String funcName ){
		if( strength < strengthReduce ){
			throw new IllegalArgumentException();
		}
		strength -= strengthReduce;
		buildLog( ObjectType.STRENGTH, -strengthReduce, strength, funcName );
		return strength;
	}
	
	/**
	 * ���ӽ��
	 * @param add		���ӵĽ��
	 * @param funcName	���õĺ���
	 * 
	 * @return ��ǰ���еĽ��
	 */
	public int addMoney( int moneyAdd, String funcName ){
		money += moneyAdd;
		
		//TODO ���������ϵͳ
		
//		Thread thr = Thread.currentThread();
//      StackTraceElement[] ele = thr.getStackTrace();
//      String func = ele[2].getMethodName();
//      System.out.println(func);

		buildLog( ObjectType.MONEY, moneyAdd, money, funcName );
		return money;
	}
	
	/**
	 * �۳���
	 * @param moneyReduce	�۳�Ľ��
	 * 
	 * @return ��ǰ���
	 */
	public int reduceMoney( int moneyReduce, String funcName ){
		if( money < moneyReduce ){
			throw new IllegalArgumentException();
		}
		money -= moneyReduce;
		buildLog( ObjectType.MONEY, -moneyReduce, money, funcName );
		return money;
	}
	
	
	public void sendPacket( ByteBuffer[] buffer ){
		
	}
	
	private void buildLog( ObjectType at, int change, int current, String funcName ){
//		String log = at.toString();
//		log += ",";
//		log += change;
//		log += ",";
//		log += current;
//		log += ",";
//		log += funcName;
		
		StringBuilder sb = new StringBuilder();
		sb.append( name );		//�û���
		sb.append( "," );
		sb.append( at );		//��Ʒ����
		sb.append( "," );
		sb.append( change );	//�仯ֵ
		sb.append( "," );
		sb.append( current );	//��ǰֵ
		sb.append( "," );
		sb.append( funcName );	//���õķ�����
		
		
		sb.toString();
		
		logger.info( sb.toString() );
	}
	
//	private log(){
//		
//	}
	public static void main ( String[] args ) {
		
		for( int i = 0; i < 10000; i++ ){
		UserInfo user = new UserInfo();
		user.addMoney( 50, "main" );
		
		user.reduceMoney( 20, "main" );
		
		user.addMoney( 20, "main" );
		user.reduceMoney( 20, "main" );
		
		user.addStrength( 500, "����" );
		user.reduceStrength( 200, "ɨ��" );
		}
	}

}
