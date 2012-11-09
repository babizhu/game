package game.task.cfg;

import game.task.templet.GoalTemplet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
 * 从配置表中初始化目标模板
 * @author liukun
 *
 */
public class GoalTempletCfg {
	private static final Map<Short,GoalTemplet> goalTemplets = new HashMap<Short, GoalTemplet>();
	private static final String FILE = "resource/goal.xml";
	
	static{
		init();
	}
	/**
	 * 通过配置表读取任务模板
	 */
	public static void init(){
		
		SAXBuilder builder = new SAXBuilder();    
		Document document;
		try {
			document = builder.build( FILE );
			Element root = document.getRootElement();  
			List<?> goalList= root.getChildren( "goal" ); 
			
			for( int i = 0; i < goalList.size(); i++ ){
				
				Element element = (Element) goalList.get( i );
				//System.out.println( element.getChildText( "name" ) );
				
				GoalTemplet templet = new GoalTemplet();
				templet.setTempletId( Short.parseShort( element.getChildText( "templetId" ) ) );
				templet.setName( element.getChildText( "name" ) );
				templet.setDesc( element.getChildText( "desc" ) );
				
				/*******************关闭打印****************************
							System.out.println( templet );
				********************************************************/
				
				GoalTemplet temp = goalTemplets.put( templet.getTempletId(), templet );
				if( temp != null ){
					throw new RuntimeException( "目标" + templet.getTempletId() + "重复了" );
				}
				
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
		
		System.out.println( "目标配置文件解析完毕" );
	}
	
	
	/**
	 * 通过模板id获取模板
	 * @param templetId
	 * @return
	 */
	public static GoalTemplet getTempletById( short templetId ){
		return goalTemplets.get( templetId );
	}
	
	public static void main(String[] args) {
		init();
		short id = 10001;
		System.out.println( GoalTempletCfg.getTempletById( id ) );
	}

}
