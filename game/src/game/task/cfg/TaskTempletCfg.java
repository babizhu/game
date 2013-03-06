package game.task.cfg;

import game.task.enums.TaskProperty;
import game.task.enums.TaskType;
import game.task.templet.TaskTempletBase;

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
	private static final Map<Short,TaskTempletBase> taskTemplets = new HashMap<Short, TaskTempletBase>();
	
	static{
		
		
	}
	private static final String FILE = "resource/task.xml";
	
	/**
	 * 初始任务的id号
	 */
	public static final short FIRST_TASK_ID = 10000;
	
	/**
	 * 通过配置表读取任务模板
	 */
	public static void init(){
		
		SAXBuilder builder = new SAXBuilder();    
		Document document;
		try {
			document = builder.build( FILE );
			Element root = document.getRootElement();  
			List<?> taskList= root.getChildren( "task" ); 
			
			for( int i = 0; i < taskList.size(); i++ ){
				
				Element element = (Element) taskList.get( i );
				//System.out.println( element.getChildText( "name" ) );
				TaskType type = TaskType.valueOf( element.getChildText( "taskType" ) );
				TaskTempletBase templet = type.createNewTemplet();
				templet.setTempletId( Short.parseShort( element.getChildText( "templetId" ) ) );
				templet.setSuccessorTempletId( element.getChildText( "successor" ) );
				templet.setName( element.getChildText( "name" ) );
				templet.setTaskProperty( TaskProperty.valueOf( element.getChildText( "taskProp" ) ) );
				templet.parseParam( element.getChildText( "param" ) );
				templet.setRequiredLevel( Short.parseShort( element.getChildText( "requiredLevel" ) ) );
				String checkNow = element.getChildText( "checkNow" );
				templet.setCheckNow( ( checkNow.isEmpty() || checkNow.equals( "0" ) ? false : true ) );
				
				String goal = element.getChildText( "goal" );
				if( goal != null ){
					short goalId = Short.parseShort( goal );
					templet.setGoal( GoalTempletCfg.getTempletById( goalId ) );
				}
				
				/*******************关闭打印****************************
							System.out.println( templet );
				********************************************************/
				
				TaskTempletBase bt = taskTemplets.put( templet.getTempletId(), templet );
				if( bt != null ){
					throw new RuntimeException( "任务" + templet.getTempletId() + "重复了" );
				}
				
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
		buildSuccessorTemplet();
		
		System.out.println( "任务配置文件解析完毕" );
	}
	
	/**
	 * 所有任务模板从配置表读取后，才能初始化后继任务，否则后继任务因为尚未被初始化为空会出问题
	 */
	private static void buildSuccessorTemplet(){
		for( Entry<Short, TaskTempletBase> e : taskTemplets.entrySet() ){
			e.getValue().buildSuccessorTemplet();
		}
	}
	/**
	 * 通过模板id获取模板
	 * @param templetId
	 * @return
	 */
	public static TaskTempletBase getTempletById( short templetId ){
		return taskTemplets.get( templetId );
	}
	public static void main(String[] args) {
		init();
		short id = 10001;
		System.out.println( TaskTempletCfg.getTempletById( id ) );
	}

}
