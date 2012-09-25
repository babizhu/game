package game.task;

import static org.junit.Assert.assertEquals;
import game.task.cfg.TaskTempletCfg;
import game.task.enums.TaskStatus;
import game.task.enums.TaskType;

import java.util.Random;

import org.junit.Test;

import user.UserInfo;
import util.ErrorCode;

public class TaskManagerTest {

	/**
	 * 	用户名不随机组成的话，manager.addFirstTask(  )函数会报错，因为同一个玩家不允许反复调用此函数
	 */
	UserInfo user = new UserInfo( null, "bbz" + new Random().nextInt( 100000) );
	
	TaskManager manager = new TaskManager( user );
	
	
	/**
	 * 分配第一个任务
	 */
	private void init(){
		
		manager.addFirstTask();//初始化，添加第一个任务
		ErrorCode code = manager.acceptTask( TaskTempletCfg.FIRST_TASK_ID );
		assertEquals( ErrorCode.SUCCESS, code );

		
	}
	@Test
	public void testAddNewTask() {
		init();
		assertEquals( 1, manager.getTasks().size() );
//		
		
	}
	/**
	 * @测试用的任务规则，不符合此规则无法完成此测试
	 * 1、任务模板id必须从10000开始<br>
	 * 2、10000号任务本身为一个DIRECT任务，可一次性完成<br>
	 * 3、10000号任务必须有2个后继任务，10001,10002<br>
	 * 4、10001号任务也必须是一个DIRECT任务，其后继任务10003<br>
	 * 5、10003号任务为接的时候就需要检查一遍的任务，并保证在接的时候直接完成，并开启10004，10005<br>
	 * 6、10005号任务是一个DIRECT_COUNT任务，需执行10次完成
	 */
	@Test
	public void testDoTask() {
		
		manager.acceptAward( (short) -1 );//试图为一个尚未完成的任务领奖
		
		int taskCount = 0;
		init();
		
		manager.doTask( TaskType.DIRECT , 10000001 );//做一个不存在的任务，玩家拥有任务数不变，依然为1
		taskCount = 1;
		assertEquals( taskCount, manager.getTasks().size() );
		
		
		manager.doTask( TaskType.DIRECT , 10000 );//做一个直接完成任务，此任务拥有2个后继任务，完成后，玩家拥有3个任务，其中未领奖状态1个，可接状态任务两个
		taskCount = 3;
		assertEquals( taskCount, manager.getTasks().size() );
		//System.out.println( manager );
		
		/********************************************************测试领奖10000号任务***********************************************/
		ErrorCode code = manager.acceptAward( (short) 100001 );//试图为一个未找到的任务领奖
		assertEquals( ErrorCode.TASK_NOT_FOUND, code );//返回任务未找到错误
		
		code = manager.acceptAward( (short) 10000 );//为完成的任务领奖，任务数量减少1个，变为2个
		assertEquals( ErrorCode.SUCCESS, code );//返回成功
		taskCount = 2;
		assertEquals( taskCount, manager.getTasks().size() );
		
		code = manager.acceptAward( (short) 10000 );//为完成的任务再领一次奖
		assertEquals( ErrorCode.TASK_NOT_FOUND, code );//返回任务未找到错误
		/********************************************************测试领奖*********************************************************/
		
		code = manager.doTask( TaskType.DIRECT , 10000 );//做一个已经完成所有流程的任务，玩家依然拥有2个任务，都处于待接状态
		taskCount = 2;
		assertEquals( taskCount, manager.getTasks().size() );
		//System.out.println( manager );
		
		
		/********************************************************测试接任务*********************************************************/
		code = manager.acceptTask( (short) 10001 );
		assertEquals( ErrorCode.LEVEL_NOT_ENOUGH, code );//接一个新任务,报等级不够
		
		user.setLevel( TaskTempletCfg.getTempletById( (short) 10001 ).getNeedLevel() );//赋值合适的用户等级
		
		code = manager.acceptTask( (short) 10001 );
		assertEquals( ErrorCode.SUCCESS, code );//成功接一个任务
		
		code = manager.acceptTask( (short) 10001 );
		assertEquals( ErrorCode.TASK_HAS_ACCEPT, code );//反复接同一个任务,报未知错误
		/********************************************************测试接任务*********************************************************/

		
		/********************************************************测试一接就完成的任务************************************************/
		manager.doTask( TaskType.DIRECT , 10001 );//直接完成10001号任务，开启一个可接新任务10003
		code = manager.acceptTask( (short) 10003 );//接10003任务，目前程序设定是开启的同时即完成该道具收集任务，并开启后继任务10004
		assertEquals( ErrorCode.SUCCESS, code );
		assertEquals( TaskStatus.NO_REWARD, manager.getTaskCopyByTempletId((short) 10003).getStatus() );//检测该任务是否完成
		/********************************************************测试一接就完成的任务************************************************/
		
		taskCount = 5;
		assertEquals( taskCount, manager.getTasks().size() );
				
		/********************************************************测试DIRECT_COUNT任务************************************************/
		manager.acceptTask( (short) 10005 );
		manager.doTask( TaskType.DIRECT_COUNT, 10005 );//做一次任务
		assertEquals( TaskStatus.ACCEPT, manager.getTaskCopyByTempletId( (short) 10005 ).getStatus() );
		

		int count = (Integer) manager.getTaskCopyByTempletId( (short) 10005 ).getParam();
		assertEquals( 1, count );
		
		for( int i = 0; i < 9; i++ ){
			manager.doTask( TaskType.DIRECT_COUNT, 10005 );//循环做剩下的9次任务
		}
		assertEquals( TaskStatus.NO_REWARD, manager.getTaskCopyByTempletId( (short) 10005 ).getStatus() );//任务已经完成
		code = manager.doTask( TaskType.DIRECT_COUNT, 10005 );//做第11次任务,已经无法找到此任务，
		assertEquals( ErrorCode.TASK_NOT_FOUND, code );
		
		count = (Integer) manager.getTaskCopyByTempletId( (short) 10005 ).getParam();

		assertEquals( 10, count );
		/********************************************************测试DIRECT_COUNT任务************************************************/

		System.out.println( manager );		
	}
	
	@Test
	public void testDb(){
		String name = "bbz4761";//测试之前临时从数据库中获取
		UserInfo user = new UserInfo( null, name);
		assertEquals( 5, user.getTaskManager().getTasks().size() );
	}
	
	@Test
	/**
	 * 如果存在2个类型相同的(如DIRECT)任务，此时做后一个直接任务（tasks循环意义上的后），确保能成功
	 */
	public void testDoSameTask() {
		init();
		manager.doTask( TaskType.DIRECT, 10000 );//做一个直接完成任务，此任务拥有2个后继任务，都属于DIRECT任务
		
		user.setLevel( TaskTempletCfg.getTempletById( (short) 10001 ).getNeedLevel() );//赋值合适的用户等级
		//分别接这两个后继任务
		manager.acceptTask( (short) 10001 );
		manager.acceptTask( (short) 10002 );
		//System.out.println( manager );
		manager.doTask( TaskType.DIRECT, 10002 );
		
		assertEquals( TaskStatus.NO_REWARD, manager.getTaskCopyByTempletId((short) 10002).getStatus() );//检测该任务是否完成
		
	}

}
