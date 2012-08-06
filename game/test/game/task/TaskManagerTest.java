package game.task;

import static org.junit.Assert.*;
import game.task.enums.TaskType;

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
		fail("Not yet implemented");
	}

	@Test
	public void testDoTask() {
		manager.doTask( TaskType.DIRECT , 10000 );//做一个直接完成任务
		manager.doTask( TaskType.PROP , 1000010 );//做一个道具收集任务
		
	}

}
