package game.fighter.cfg;

import game.fighter.NpcFighter;

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
public class NpcFighterTempletCfg {
	private static final Map<Short,NpcFighter> npcFighters = new HashMap<Short, NpcFighter>();
	
	static{
		
		
	}
	private static final String FILE = "resource/npc-fighter.xml";
	
		
	/**
	 * 通过配置表读取Npc战士模板
	 */
	public static void init(){
		
		SAXBuilder builder = new SAXBuilder();    
		Document document;
		try {
			document = builder.build( FILE );
			Element root = document.getRootElement();  
			List<?> fighterList= root.getChildren( "fight" ); 
			
			for( int i = 0; i < fighterList.size(); i++ ){
				NpcFighter npc = new NpcFighter( );
				Element element = (Element) fighterList.get( i );
				npc.setId( Short.parseShort( element.getChildText( "id" ) ) );
				npc.setName( element.getChildText( "name" ) );
				npc.setDesc( element.getChildText( "desc" ) );
				npc.setHpMax( Integer.parseInt( element.getChildText( "hpMax" ) ) );
				npc.setHp( npc.getHpMax() );
				npc.setSpMax( Integer.parseInt( element.getChildText( "spMax" ) ) );
				npc.setSp( npc.getSpMax() );
				npc.setPhyAttack( Integer.parseInt( element.getChildText( "phyAttack" ) ) );
				npc.setPhyDefend( Integer.parseInt( element.getChildText( "phyDefend" ) ) );
				npc.setSpeed( Integer.parseInt( element.getChildText( "speed" ) ) );
				npc.setHitRate( Integer.parseInt( element.getChildText( "hitRate" ) ) );
				npc.setDodgeRate( Integer.parseInt( element.getChildText( "dodgeRate" ) ) );
				npc.setCrit( Integer.parseInt( element.getChildText( "crit" ) ) );
				npc.setUnCrit( Integer.parseInt( element.getChildText( "unCrit" ) ) );
				npc.setBlock( Integer.parseInt( element.getChildText( "block" ) ) );
				npc.setUnBlock( Integer.parseInt( element.getChildText( "unBlock" ) ) );
			
				/*******************关闭打印****************************
							System.out.println( npc );
				********************************************************/
				
				NpcFighter temp = npcFighters.put( npc.getId(), npc );
				if( temp != null ){
					throw new RuntimeException( "npc战士" + npc.getId() + "重复了" );
				}
				
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
		System.out.println( "npc 战士配置文件解析完毕" );
	}
	
	
	/**
	 * 通过id获取战士的拷贝
	 * @param templetId
	 * @return
	 */
	public static NpcFighter getCopyById( short templetId ){
		NpcFighter npc = npcFighters.get( templetId );
		return new NpcFighter( npc );
	}
	public static void main(String[] args) {
		init();
		short id = 2;
		System.out.println( getCopyById(id));
	}

}
