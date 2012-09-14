package game.packages.packs;

import java.io.IOException;
import java.nio.ByteBuffer;

import user.UserInfo;
import game.packages.BasePackage;


/**
 * 装备升级
 * @author liukun
 * 2012-9-14 下午03:12:14
 */
public class EquipmentLevelUpPackage  extends BasePackage {
	
	@Override
	public void run(UserInfo user, ByteBuffer buf) throws IOException {
		System.err.println( this.getClass());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println( this.getClass() + " end" );
	}
}
