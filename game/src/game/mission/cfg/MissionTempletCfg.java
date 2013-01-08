package game.mission.cfg;

import game.battle.auto.Formation9;
import game.battle.formation.IFormation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class MissionTempletCfg {
private static final Map<Short,MissionTemplet> missions = new HashMap<Short, MissionTemplet>();
	
	static{
		
		
	}
	private static final String FILE = "resource/mission.xml";
	
		
	/**
	 * 通过配置表读取Npc战士模板
	 */
	public static void init(){
		
		SAXBuilder builder = new SAXBuilder();    
		Document document;
		try {
			document = builder.build( FILE );
			Element root = document.getRootElement();  
			List<?> list = root.getChildren( "mission" ); 
			
			for( int i = 0; i < list.size(); i++ ){
				MissionTemplet templet = new MissionTemplet( );
				Element element = (Element) list.get( i );
				templet.setId( Short.parseShort( element.getChildText( "id" ) ) );
				templet.setName( element.getChildText( "name" ) );
				templet.setDesc( element.getChildText( "desc" ) );
				
			
				/*******************关闭打印****************************
							System.out.println( npc );
				********************************************************/
				
				MissionTemplet temp = missions.put( templet.getId(), templet );
				if( temp != null ){
					throw new RuntimeException( "主线任务" + temp.getId() + "重复了" );
				}
				
				
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
		System.out.println( "npc 战士配置文件解析完毕" );
	}
	
	IFormation parseFormation( String str ){
	
		IFormation formation = new Formation9( fightersList, isLeft, pet );
		return formation;
	}
	
	/**
	 * 通过id获取主线任务
	 * @param templetId
	 * @return
	 */
	public static MissionTemplet getCopyById( short templetId ){
		return missions.get( templetId );
	}
	public static void main(String[] args) {
		init();
		short id = 2;
		System.out.println( getCopyById(id));
	}

}
