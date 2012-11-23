package game.packages.packs;


import game.packages.PackageManager;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.xsocket.connection.IBlockingConnection;

public class EquipmentLevelUpPackageTest extends BasePackageTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * 
	 * @param id	要升级的装备id
	 * @param typ	升级类型   普通，点券？
	 * @return
	 */
	public ByteBuffer createContent( long id, byte type ){
		ByteBuffer buf = createEmptyPackage(1024);
		//BaseUtil.encodeString( buf, name );
		return buf;
	}
	
	/**
	 * 发送一个完整的登陆包
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	public ByteBuffer sendPackage( IBlockingConnection nbc,  long id, byte type  ) throws IOException{
		//this.nbc = nbc;
		ByteBuffer buf = createContent( id, type );
		sendPacket( nbc, buf );
		//return getData( nbc );
		return null;
	}

	@Override
	public short getPacketNo() {
		return PackageManager.EQUIPMENT_LEVEL_UP.toNum();
	}

}
