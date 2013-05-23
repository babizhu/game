package game.mission.cfg;

import game.battle.Pet;
import game.battle.formation.IFormation;
import game.battle.auto.web.Formation9;
import game.fighter.FighterBase;
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
				Element element = (Element) list.get( i );
				short id = Short.parseShort( element.getChildText( "id" ) );
				String name = element.getChildText( "name" );
				String desc = element.getChildText( "desc" );
				List<IFormation> formations = parseFormation( element );
				MissionTemplet templet = new MissionTemplet( id, name, desc, formations );
			
				/*******************关闭打印****************************
							System.out.println( templet );
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
		boolean isLeft = false;//NPC通常作为防守方出现
		for( int i = 0; i < 3; i++ ){
			String nodeName = "npc" + i;
			String content =  element.getChildText( nodeName );
			if( content == null || content.isEmpty() ){
				continue;
			}
			List<FighterBase> fightersList = parseFighterList( content );
			Pet pet = null;
			IFormation formation = new Formation9( fightersList, isLeft, pet );
			
			formations.add( formation );
		}
		return formations;
	}
	
	private static List<FighterBase> parseFighterList( String content ){
		List<FighterBase> list = new ArrayList<FighterBase>();
		String[] fighterArr = content.split( "\\|" );
		for( String s : fighterArr ){
			Short fighterId = Short.parseShort( s.split( "," )[0] );
			byte pos = Byte.parseByte( s.split( "," )[1] );
			
			if( pos < 0 || pos > 8 ) {
				throw new RuntimeException( content + "错误，配置表中的战士位置必须满足: 0 <= pos <= 8!" );
			}
			
			NpcFighter f = NpcFighterTempletCfg.getNpcCloneById( fighterId );
			f.setPosition( pos );
			list.add( f );
		}
		//按位置排序
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
		System.out.println( getTempletById( (short) 1) );
		
	}

}
