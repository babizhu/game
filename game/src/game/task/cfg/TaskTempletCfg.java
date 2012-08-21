package game.task.cfg;

import game.task.enums.TaskProperty;
import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
 * 从配置表中初始化模板
 * @author liukun
 *
 */
public class TaskTempletCfg {
	private static final Map<Short,BaseTaskTemplet> taskTemplets;
	
	static{
		taskTemplets = new HashMap<Short, BaseTaskTemplet>();
		init();
		
	}
	private static final String FILE = "resource/task.xml";
	
	/**
	 * 通过配置表读取任务模板
	 */
	private static void init(){
		
		SAXBuilder builder = new SAXBuilder();    
		Document document;
		try {
			document = builder.build( FILE );
			Element root = document.getRootElement();  
			List<?> taskList= root.getChildren( "task" ); 
			
			for( int i = 0; i < taskList.size(); i++ ){
				Element element = (Element) taskList.get( i );
				TaskType type = TaskType.valueOf( element.getChildText( "task_type" ) );
				BaseTaskTemplet templet = type.createNewTemplet();
				templet.setTempletId( Short.parseShort( element.getChildText( "id" ) ) );
				templet.setSuccessorTempletId( element.getChildText( "successor" ) );
				templet.setName( element.getChildText( "name" ) );
				templet.setTaskProperty( TaskProperty.valueOf( element.getChildText( "task_prop" ) ) );
				templet.parseParam( element.getChildText( "param" ) );
				templet.setNeedLevel( Byte.parseByte( element.getChildText( "need_level" ) ) );
				String checkNow = element.getChildText( "check_now" );
				templet.setCheckNow( ( checkNow.isEmpty() || checkNow.equals( "0" ) ? false : true ) );
				
				/*******************关闭打印****************************
							System.out.println( templet );
				********************************************************/
				
				taskTemplets.put( templet.getTempletId(), templet );
				
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
		

		   
		
		buildOpenTemplet();
		
	}
	
	/**
	 * 所有任务模板从配置表读取后，才能初始化后继任务，否则后继任务因为尚未被初始化为空会出问题
	 */
	private static void buildOpenTemplet(){
		for( Entry<Short, BaseTaskTemplet> e : taskTemplets.entrySet() ){
			e.getValue().buildSuccessorTemplet();
		}
	}
	/**
	 * 通过模板id获取模板
	 * @param templetId
	 * @return
	 */
	public static BaseTaskTemplet getTempletById( short templetId ){
		return taskTemplets.get( templetId );
	}
	public static void main(String[] args) {
		short id = 10001;
		System.out.println( TaskTempletCfg.getTempletById( id ) );
	}

}
