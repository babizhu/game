package test;

import game.task.BaseTask;
import game.task.DirectCountTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 测试HashMap和List,谁的循环速度快
 * 结论，总数小于100的时候，List要快点，总数大于100的时候，map要快一点
 * 当然这个100是泛指，并不是准确的100
 * @author liukun
 *
 */
public class PerfomanceTest {

	private static Map<Short,BaseTask> taskMap = new HashMap<Short,BaseTask>();
	private static List<BaseTask> 	taskList = new ArrayList<BaseTask>();
	static int count = 10;
	static void init()
	{
		for( int i = 0; i < count; i++ ){
			DirectCountTask task = new DirectCountTask( null );
			taskMap.put( (short)i, task );
		}
		
		for( int i = 0; i < count; i++ ){
			DirectCountTask task = new DirectCountTask( null );
			taskList.add( task );
		}
		
	}
	/**
	 * 循环haspMap所需要的时间
	 */
	static void test1(){
		long begin = System.nanoTime();
		for( int j = 0; j < 100000; j++ ){
			for( Entry<Short, BaseTask> e : taskMap.entrySet() ){
				BaseTask t = e.getValue();
				int i = 0;
				if( t.getAcceptAwardTime() == 10 ){
					i++;
				}
			}
		}
		System.out.println( "map 的循环时间为:" + (System.nanoTime() - begin) / 1000000000f );
	}
	
	/**
	 * 循环ArrayList所需要的时间
	 */
	static void test2(){
		long begin = System.nanoTime();
		for( int j = 0; j < 100000; j++ ){
			for( BaseTask t : taskList ){
				int i = 0;
				if( t.getAcceptAwardTime() == 10 ){
					i++;
				}
			}
		}
		System.out.println( "list的循环时间为:" + (System.nanoTime() - begin) / 1000000000f );
	}
	public static void main(String[] args) {
		init();
		test1();
		test2();
		
	}
}
