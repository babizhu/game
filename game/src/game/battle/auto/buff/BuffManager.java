/**
*	作者：admin
*	时间：2011-10-20 下午05:19:20
*	文件：BuffManager.java
*
*
*
*	功能：
* 
*/
package game.battle.auto.buff;

import game.battle.BaseBattle;
import game.battle.BaseBuff;
import game.battle.BuffRunPoint;

import java.util.ArrayList;
import java.util.List;

public class BuffManager {
	
	private List<BaseBuff>						buffs = new ArrayList<BaseBuff>();	
	public BuffManager( ) {
		super();
	}
	
	/**
	 * 清除该战士所有的buff效果
	 */
	public void removeAllBuff( ){
		buffs.clear();
	}
	
	/**
	 * 添加一个buff
	 * @param buff
	 * @return
	 * 		true	成功
	 */
	public boolean add( BaseBuff buff ){
		buffs.add( buff );		//有可能需要考虑buff之间的兼容关系, 是一个很麻烦的高难度过程
		//如果没问题，添加成功
		if( true ){
			buff.init();
		}
		return true;
	}
	
	/**
	 * 根据时间点参数，运行相应的buff内容
	 * @param damage
	 * @param pt
	 * @param battle
	 * @return
	 * 
	 * 注意：这里只返回伤害值，除HP以外其余的buff效果，例如增加SP等，直接在run()内进行处理
	 */
	public int run( int damage, BuffRunPoint pt, BaseBattle battle ){
		int tempDamage = damage;//一个buf所产生的临时伤害值
		for( BaseBuff buf : buffs ){
			
			if( buf.getBuffRunPoint() == pt && !buf.isRemove() ){
				
				tempDamage += buf.run( battle, tempDamage );
			}
		}		
		return tempDamage;
	}	

	public static void main(String[] args) {
	
		List<BaseBuff> list = new ArrayList<BaseBuff>();
		for( int i = 0; i < 10; i++ ){
			BaseBuff buf = new B001( null );
			list.add( buf );
			
		}
		int i = 0;
		for( BaseBuff buf : list ){
			if( i++ == 5 ){
				list.remove( i );
				list.remove( buf );
			}
			
		}
	}

}
