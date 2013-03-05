package game.task.cfg;

import static org.junit.Assert.*;
import game.task.enums.TaskProperty;
import game.task.enums.TaskType;
import game.task.templet.TaskTempletBase;

import org.junit.Test;

public class TaskTempletCfgTest {

	@Test
	public void testGetTempletById() {
//		long begin = System.nanoTime();
//		for( int i = 0; i < 10000000; i++ ){
		TaskTempletBase t = TaskTempletCfg.getTempletById( (short) 10 );
		assertNull( t );//不存在的任务模板
		t = TaskTempletCfg.getTempletById( (short) 10000 );
		assertEquals( t.getName(), "寻找刘老爷" );
		assertEquals( t.getTaskType(), TaskType.DIRECT );
		
		TaskTempletBase successor = TaskTempletCfg.getTempletById( (short) 10001 );
		assertEquals( successor, t.getSuccessorTemplet()[0] );//测试后继任务
		
		t = TaskTempletCfg.getTempletById( (short) 10001 );
		assertEquals( t.getTaskProperty(), TaskProperty.MAIN );
		assertEquals( t.getName(), "消灭巨狼" );
//		}
//		System.out.println( (System.nanoTime() - begin) / 1000000000f );
	}

}
