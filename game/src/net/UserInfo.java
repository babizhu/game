package net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserInfo {
	private final static Logger logger = LoggerFactory.getLogger( UserInfo.class ); 
	
	void testLogback(){
		logger.info( "testLogback" );
		System.out.println( "dd" );
	}


	public static void main(String[] args) {
		UserInfo u = new UserInfo();
		u.testLogback();
	}
}
