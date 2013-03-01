package game.battle.buff.cfg;

import game.battle.formation.ChooseFighters;
import game.battle.formula.Formula;
import game.battle.skill.SkillEffect;
import game.battle.skill.SkillTemplet;
import game.fighter.FighterAttribute;

import java.io.IOException;
import java.util.ArrayList;
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
public class BuffTempletCfg {
	
private static final Map<Byte,SkillTemplet> skillTemplets = new HashMap<Byte, SkillTemplet>();
	
	/**
	 * 此配置表必须先于NpcFighterTempletCfg初始化（每个npc都有相应的技能），因此无需提前手动调用
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
			List<Element> fighterList= root.getChildren( "skill" ); 
			
			for( int i = 0; i < fighterList.size(); i++ ){
				Element element = (Element) fighterList.get( i );
				byte id = Byte.parseByte( element.getChildText( "id" ) );
				SkillTemplet templet = new SkillTemplet( id );
				templet.setName( element.getChildText( "name" ) );
				templet.setDesc( element.getChildText( "desc" ) );
				String enemy = element.getChildText( "enemy" );
				
				if( enemy != null && !enemy.isEmpty() ){
					templet.setEnemy( ChooseFighters.valueOf( enemy ) );
					templet.setEffectOnEnemy( parseSkillEffect( element.getChildText( "enemy_effect" ) ) );
				}
				
				String friend = element.getChildText( "friend" );
				if( friend != null && !friend.isEmpty() ){
					templet.setFriend( ChooseFighters.valueOf( friend ) );
					templet.setEffectOnFriend( parseSkillEffect( element.getChildText( "friend_effect" ) ) );
				}
				
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
	 * 解析技能效果：ENEMY_HP,SkillAttackFormula,0|SP,DirectOutputFormula,-20
	 * @param str
	 * @return
	 */
	private static List<SkillEffect> parseSkillEffect( String str ){
		List<SkillEffect> skillEffects = new ArrayList<SkillEffect>();
		String[] effect = str.split( "\\|" );
		for( String s : effect ){
			SkillEffect se = new SkillEffect();
			String[] content = s.split( "," );
			se.setAttribute( FighterAttribute.valueOf( content[0] ) );
			se.setFormula( Formula.valueOf( content[1] ) );
			se.setArgument( parseArgument( content ) );
			skillEffects.add( se );
		}
		
		return skillEffects;
	}
	
	/**
	 * 解析技能效果中的参数信息，由于这个数目是可变的，因此单独提出来处理
	 * @param content	ENEMY_HP,公式1,1.4..............................1.4是我们要解析的内容
	 * @return
	 */
	private static float[] parseArgument( String[] content ){
		int magicNumber = 2;//2 for FighterAttribute and formula
		int size = content.length - magicNumber;//2 for FighterAttribute和所需的公式
		
		float[] arguments = new float[size];
		for( int i = 0; i < size; i++ ){
			arguments[i] = Float.parseFloat( content[magicNumber+i] );//
		}
		return arguments;
	}
	
	/**
	 * 通过id获取技能引用
	 * @param templetId
	 * @return
	 */
	public static SkillTemplet getSkillTempletById( byte templetId ){
		return skillTemplets.get( templetId );
	}


	public static void main(String[] args) {
		
		for( SkillTemplet t : skillTemplets.values() ){
			
			System.out.println( t );
		}
		
	}

}
