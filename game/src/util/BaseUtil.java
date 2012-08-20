package util;

import java.nio.ByteBuffer;

/**
 * 系统中最基础的一些工具，不仅仅针对游戏
 * @author liukun
 *
 */
public class BaseUtil {

	public static final float TO_SECOND = 1000000000f;
	/**
	 * 编码字符串，先放入一个short的字符串长度，再放入字符串的内容<br>
	 * 字符串长度不得大于Short.MAX_VALUE
	 * @param buf
	 * @param content
	 */
	public static void encodeString( ByteBuffer buf, String content ){
	
		byte[] temp = content.getBytes();
		if( temp.length > Short.MAX_VALUE ){
			throw new IllegalArgumentException( "字符串长度超限" );
		}
		buf.putShort( (short) temp.length );
		buf.put( temp );
		
	}
	
	/**
	 * 解码字符串，从缓冲区中先读出一个short的字符串长度，然后读取内容并生产字符串
	 * 字符串长度不得大于Short.MAX_VALUE
	 * @param buf
	 * @param content
	 */
	public static String decodeString( ByteBuffer buf ){
	
		short len = buf.getShort();
		byte[] content = new byte[len];
		buf.get( content );
		return new String( content );
	}
	
	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate( 100 );
		encodeString(buf, "content中文");
		buf.flip();
		System.out.println( decodeString(buf) );	
	}
}
