package game.packages.packs;

import java.nio.ByteBuffer;

import user.UserInfo;
import game.packages.BasePackage;

/**
 * 
 * @author liukun
 * 2012-8-15
 */
public class InitPackage extends BasePackage {

	@Override
	public void run( UserInfo user, ByteBuffer buf ) {

	}

	@Override
	public short getPacketNo() {
		return 0;
	}

	/**
	 * 
	 */
	void test(){
		
	}
}
