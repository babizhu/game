package game.task.cfg;

import static org.junit.Assert.*;
import game.task.enums.TaskType;
import game.task.templet.BaseTaskTemplet;
import game.task.templet.TaskProperty;

import org.junit.Test;

public class TaskTempletCfgTest {

	@Test
	public void testGetTempletById() {
//		long begin = System.nanoTime();
//		for( int i = 0; i < 10000000; i++ ){
		BaseTaskTemplet t = TaskTempletCfg.getTempletById( 10 );
		assertNull( t );//不存在的任务模板
		t = TaskTempletCfg.getTempletById( 10000 );
		assertEquals( t.getName(), "寻找刘老爷" );
		assertEquals( t.getTaskType(), TaskType.DIRECT );
		
		BaseTaskTemplet successor = TaskTempletCfg.getTempletById( 10001 );
		assertEquals( successor, t.getSuccessorTemplet()[0] );//测试后继任务
		
		t = TaskTempletCfg.getTempletById( 10001 );
		assertEquals( t.getTaskProperty(), TaskProperty.MAIN );
//		}
//		System.out.println( (System.nanoTime() - begin) / 1000000000f );
	}

}
