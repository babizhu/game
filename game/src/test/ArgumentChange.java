package test;

import user.UserInfo;

/**
 * 测试传入的参数（一个类的引用）会否被在函数中改变
 * 
 * test函数中传入的user本身不会被user = u1;这句代码改变
 * @author admin
 * 2012-8-31 下午04:08:15
 */
public class ArgumentChange {
	
	void test( UserInfo user ){
		UserInfo u1 = new UserInfo();
		u1.setName( "babizhu" );
		user = u1;
	}

	public static void main(String[] args) {
		ArgumentChange ac = new ArgumentChange();
		UserInfo user = new UserInfo();
		user.setName( "liukun" );
		
		System.out.println( "调用test函数之前：" + user.getName() );
		ac.test( user );
		System.out.println( "调用test函数之后：" + user.getName() );
	}
	
}
