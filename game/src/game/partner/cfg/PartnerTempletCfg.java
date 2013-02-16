package game.partner.cfg;

import game.partner.PartnerTemplet;

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
public class PartnerTempletCfg {
	
	/**
	 * short for templetId
	 */
	private static final Map<Short,PartnerTemplet> templets = new HashMap<Short,PartnerTemplet>();
	private static final String FILE = "resource/partner.xml";
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
				PartnerTemplet templet = parse( element );
				
				/*******************关闭打印****************************
							System.out.println( templet );
				********************************************************/
				
				PartnerTemplet t = templets.put( templet.getTempletId(), templet );
				if( t != null ){
					throw new RuntimeException( "伙伴" + templet.getTempletId() + "重复了" );
				}
				
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.println( "伙伴配置文件解析完毕" );
	}
			
	
	private static PartnerTemplet parse(Element element) {
		PartnerTemplet templet = new PartnerTemplet();
		return templet;
	}


	/**
	 * 通过模板id获取模板
	 * @param templetId
	 * @return
	 */
	public static PartnerTemplet getTempletById( short templetId ){
		return templets.get( templetId );
	}
	
	public static void main(String[] args) {
		init();
		System.out.println( templets );
	}

}
