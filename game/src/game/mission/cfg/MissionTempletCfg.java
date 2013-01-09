package game.mission.cfg;

import game.battle.Pet;
import game.battle.auto.Formation9;
import game.battle.formation.IFormation;
import game.fighter.BaseFighter;
import game.fighter.NpcFighter;
import game.fighter.cfg.NpcFighterTempletCfg;

import java.io.IOException;
import java.util.ArrayList;
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
				templet.setFormations( parseFormation( element ) );
			
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
		
		System.out.println( "主线关卡配置表解析完毕" );
	}
	
	private static List<IFormation> parseFormation( Element element ){
		List<IFormation> formations = new ArrayList<IFormation>();
		boolean isLeft = false;
		for( int i = 0; i < 3; i++ ){
			String nodeName = "npc" + i;
			String content =  element.getChildText( nodeName );
			if( content == null || content.isEmpty() ){
				continue;
			}
			List<BaseFighter> fightersList = parseFighterList( content );
			Pet pet = null;
			IFormation formation = new Formation9( fightersList, isLeft, pet );
			
			formations.add( formation );
		}
		return formations;
	}
	
	private static List<BaseFighter> parseFighterList( String content ){
		List<BaseFighter> list = new ArrayList<BaseFighter>();
		String[] fighterArr = content.split( "\\|" );
		for( String s : fighterArr ){
			Short fighterId = Short.parseShort( s.split( "," )[0] );
			byte pos = Byte.parseByte( s.split( "," )[1] );
			
			if( pos < 0 || pos > 8 ) {
				//System.out.println( str );
				throw new RuntimeException( content + "错误，配置表中的战士位置必须满足: 0 <= pos <= 8!" );
			}
			
			NpcFighter f = NpcFighterTempletCfg.getNpcCloneById( fighterId );
			f.setPosition( pos );//作为防守方，主动+9
			list.add( f );
		}
		return list;
	}
	
	/**
	 * 通过id获取主线任务
	 * @param templetId
	 * @return
	 */
	public static MissionTemplet getTempletById( short templetId ){
		return  missions.get( templetId );
	}
	
	
	public static void main(String[] args) {
		init();
		System.out.println( getTempletById( (short) 2) );
		
	}

}
