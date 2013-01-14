package game.battle.skill.cfg;

import game.battle.skill.SkillTemplet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
 * 从配置表中初始化Npc Fighter模板
 * @author liukun
 *
 */
public class SkillTempletCfg {
	private static final Map<Byte,SkillTemplet> skillTemplets = new HashMap<Byte, SkillTemplet>();
	
	/**
	 * 此配置表必须先于NpcFighterTempletCfg初始化，因此无需提前手动调用
	 */
	static{
		init();
		
	}
	private static final String FILE = "resource/skill.xml";
	
		
	/**
	 * 通过配置表读取Npc战士模板
	 */
	private static void init(){
		
		SAXBuilder builder = new SAXBuilder();    
		Document document;
		try {
			document = builder.build( FILE );
			Element root = document.getRootElement();  
			List<?> fighterList= root.getChildren( "skill" ); 
			
			for( int i = 0; i < fighterList.size(); i++ ){
				SkillTemplet templet = new SkillTemplet( );
				Element element = (Element) fighterList.get( i );
				templet.setId( Byte.parseByte( element.getChildText( "id" ) ) );
				templet.setName( element.getChildText( "name" ) );
				templet.setDesc( element.getChildText( "desc" ) );
				
				/*******************关闭打印****************************
							System.out.println( templet );
				********************************************************/
				
				SkillTemplet temp = skillTemplets.put( templet.getId(), templet );
				if( temp != null ){
					throw new RuntimeException( "技能" + templet.getId() + "重复了" );
				}
				
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
		System.out.println( "技能配置文件解析完毕" );
	}
	
	
	/**
	 * 通过id获取战士的引用
	 * @param templetId
	 * @return
	 */
	public static SkillTemplet getSkillTempletById( byte templetId ){
		return skillTemplets.get( templetId );
	}
	public static void main(String[] args) {
		
		byte id = 1;
		System.out.println( getSkillTempletById(id));
	}

}
