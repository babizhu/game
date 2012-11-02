package define;

import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import util.SystemTimer;

/**
 *  保留系统信息，诸如启动时间，开服时间等一系列参数
 *  变量没法用final
 * @author admin
 *
 */
public class SystemCfg {
	
	private static final String FILE = "config/system.xml";

	/**
	 * 系统本次启动时间
	 */
	public static final long 	START_MILS = SystemTimer.currentTimeMillis();
	
	/**
	 * 游戏区id
	 */
	public  static int 	GAME_DISTRICT;
	
	/**
	 * 端口
	 */
	public static  int 	PORT;
	
	/**
	 * 系统版本
	 */
	public static String	VERSION;
	
	static{
		GAME_DISTRICT = 0;
		SAXBuilder builder = new SAXBuilder();    
		Document document;
		try {
			document = builder.build( FILE );
			Element root = document.getRootElement();  
			GAME_DISTRICT = Integer.parseInt( root.getChildText( "game_district" ) );
			PORT = Integer.parseInt( root.getChildText( "port" ) );
			VERSION = root.getChildText( "version" );

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}	

}
