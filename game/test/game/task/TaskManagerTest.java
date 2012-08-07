package game.task;

import static org.junit.Assert.*;
import game.task.cfg.TaskTempletCfg;
import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import user.UserInfo;

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
		BaseTaskTemplet templet = TaskTempletCfg.getTempletById( 10000 );
		manager.addFirstTask( templet );
		assertEquals( 1, manager.getTasks().size() );
//		
		
	}
	@Test
	public void testDoTask() {
		int taskCount = 0;
		user.setName( "bbz" );
		BaseTaskTemplet templet = TaskTempletCfg.getTempletById( 10000 );
		manager.addFirstTask( templet );
		
		System.out.println( manager );
		
		manager.doTask( TaskType.DIRECT , 10000001 );//做一个不存在的任务，玩家拥有任务数不变，依然为1
		taskCount = 1;
		assertEquals( taskCount, manager.getTasks().size() );
		
		
		manager.doTask( TaskType.DIRECT , 10000 );//做一个直接完成任务，此任务拥有2个后继任务，完成后，玩家拥有3个任务
		taskCount = 3;
		assertEquals( taskCount, manager.getTasks().size() );
		
		
		manager.doTask( TaskType.DIRECT , 10000 );//做一个已经完成但尚未领奖的任务，玩家依然拥有3个任务
		taskCount = 3;
		assertEquals( taskCount, manager.getTasks().size() );
		System.out.println( manager );
		
		/**
		 * 存在2个未完成的直接任务，此时做后一个直接任务（list意义上的后），确保能成功
		 */
		//TODO 代码慢慢加
		
	}
	
	

}
