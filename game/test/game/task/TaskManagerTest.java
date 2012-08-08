package game.task;

import static org.junit.Assert.*;
import game.task.cfg.TaskTempletCfg;
import game.task.enums.TaskStatus;
import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import user.UserInfo;
import util.ErrorCode;

public class TaskManagerTest {

	UserInfo user = new UserInfo();
	
	TaskManager manager = new TaskManager( user );
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testChangeStatus() {
		//fail("Not yet implemented");
	}
	@Test
	public void testAddNewTask() {
		user.setName( "bbz" );
		BaseTaskTemplet templet = TaskTempletCfg.getTempletById( (short) 10000 );
		manager.addFirstTask( templet );
		assertEquals( 1, manager.getTasks().size() );
//		
		
	}
	@Test
	public void testDoTask() {
		
		manager.AcceptAward( (short) -1 );//试图为一个尚未完成的任务领奖
		
		
		int taskCount = 0;
		user.setName( "bbz" );
		BaseTaskTemplet templet = TaskTempletCfg.getTempletById( (short) 10000 );
		manager.addFirstTask( templet );//初始化，添加第一个任务
		
		//System.out.println( manager );
		
		manager.doTask( TaskType.DIRECT , 10000001 );//做一个不存在的任务，玩家拥有任务数不变，依然为1
		taskCount = 1;
		assertEquals( taskCount, manager.getTasks().size() );
		
		
		manager.doTask( TaskType.DIRECT , 10000 );//做一个直接完成任务，此任务拥有2个后继任务，完成后，玩家拥有3个任务，其中未领奖状态1个，可接状态任务两个
		taskCount = 3;
		assertEquals( taskCount, manager.getTasks().size() );
		//System.out.println( manager );
		
		ErrorCode code = manager.AcceptAward( (short) 100001 );//试图为一个尚未完成的任务领奖
		assertEquals( ErrorCode.TASK_NOT_FOUND, code );//返回任务未找到错误
		
		code = manager.AcceptAward( (short) 10000 );//为完成的任务领奖，任务数量减少1个
		assertEquals( ErrorCode.SUCCESS, code );//返回成功
		taskCount = 2;
		assertEquals( taskCount, manager.getTasks().size() );
		
		manager.doTask( TaskType.DIRECT , 10000 );//做一个已经完成但尚未领奖的任务，玩家依然拥有2个任务，10000任务已经完成，其余两个处于待接状态
		taskCount = 2;
		assertEquals( taskCount, manager.getTasks().size() );
		//System.out.println( manager );
		
		code = manager.acceptTask( (short) 10001 );
		assertEquals( ErrorCode.LEVEL_NOT_ENOUGH, code );//接一个新任务,报等级不够
		
		user.setLevel( TaskTempletCfg.getTempletById( (short) 10000 ).getNeedLevel() );//赋值合适的用户等级
		
		code = manager.acceptTask( (short) 10001 );
		assertEquals( ErrorCode.SUCCESS, code );//成功接一个任务
		
		code = manager.acceptTask( (short) 10001 );
		assertEquals( ErrorCode.UNKNOW_ERROR, code );//接一个已经接过了的任务
		

		
		manager.doTask( TaskType.DIRECT , 10001 );//直接完成10001号任务，开启一个可接新任务10003
		code = manager.acceptTask( (short) 10003 );//接10003任务，目前程序设定是开启的同时即完成该道具收集任务
		assertEquals( ErrorCode.SUCCESS, code );
		assertEquals( TaskStatus.NO_REWARD, manager.getTaskByTempletId((short) 10003).getStatus() );//检测该任务是否完成
		
		System.out.println( manager );
		/**
		 * 存在2个未完成的直接任务，此时做后一个直接任务（list意义上的后），确保能成功
		 */
		//TODO 代码慢慢加
		
	}
	
	

}
