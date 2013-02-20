package util;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

public class BaseUtilTest {

	

	@Test
	public void testDecodeString() {
		ByteBuffer buf = ByteBuffer.allocate(100);
		UtilBase.encodeString(buf, "content中文");
		buf.flip();
		assertEquals( "content中文", UtilBase.decodeString( buf ) );
		
		buf.flip();
		buf.putShort( (short) 10000 );
		buf.flip();
		UtilBase.decodeString(buf);
	}

	@Test
	public void testIsWindowsOS() {
		assertEquals( true, UtilBase.isWindowsOS() );
	}

}
