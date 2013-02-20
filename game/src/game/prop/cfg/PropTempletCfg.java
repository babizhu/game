package game.prop.cfg;

import game.prop.enums.PropType;
import game.prop.templet.PropTempletBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * 道具配置表
 * @author liukun
 * 2012-10-31 上午08:22:31
 */
public class PropTempletCfg {
	
	/**
	 * short for templetId
	 */
	private static final Map<Short,PropTempletBase> propTemplets = new HashMap<Short,PropTempletBase>();
	private static final String FILE = "resource/prop.xml";
	static{
		//init();		
	}
	
	/**
	 * 通过配置表读取道具模板
	 */
	public static void init(){
		
		SAXBuilder builder = new SAXBuilder();    
		Document document;
		try {
			document = builder.build( FILE );
			Element root = document.getRootElement();  
			List<?> taskList= root.getChildren( "prop" ); 
			
			for( int i = 0; i < taskList.size(); i++ ){
				
				Element element = (Element) taskList.get( i );
				//System.out.println( element.getChildText( "name" ) );
				PropType type = PropType.valueOf( element.getChildText( "propType" ) );
				PropTempletBase templet = type.create();
				templet.parse( element );
				
				/*******************关闭打印****************************
							System.out.println( templet );
				********************************************************/
				
				PropTempletBase bpt = propTemplets.put( templet.getTempletId(), templet );
				if( bpt != null ){
					throw new RuntimeException( "道具" + templet.getTempletId() + "重复了" );
				}
				
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.println( "道具配置文件解析完毕" );
	}
			
	
	/**
	 * 通过模板id获取模板
	 * @param templetId
	 * @return
	 */
	public static PropTempletBase getTempletById( short templetId ){
		return propTemplets.get( templetId );
	}
	
	public static void main(String[] args) {
		init();
		System.out.println( propTemplets );
	}

}
