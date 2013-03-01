/**
*	作者：liukun
*	时间：2011-10-20 下午05:19:20
*	文件：BuffManager.java
*
*
*
*	功能：
* 
*/
package game.battle.auto.buff;

import game.battle.buff.BuffBase;
import game.battle.buff.BuffRunPoint;

import java.util.ArrayList;
import java.util.List;

public class BuffManager {
	
	private List<BuffBase>						buffs = new ArrayList<BuffBase>();
	public BuffManager( ) {
		super( );
	}
	
	/**
	 * 清除该战士所有的负面buff效果
	 */
	public void removeDeBuff(  ){
		for( BuffBase buff : buffs ){
			if( buff.isDeBuff() ){
				buff.setRemove( true );
			}
		}
	}
	
	/**
	 * 添加一个buff
	 * @param buff
	 * @return
	 * 		true	成功
	 */
	public boolean add( BuffBase buff ){
		buffs.add( buff );		//有可能需要考虑buff之间的兼容关系, 是一个很麻烦的高难度过程
		//如果没问题，添加成功
		if( true ){
			buff.start();
		}
		return true;
	}
	
	/**
	 * 根据时间点参数，运行相应的buff内容
	 * @param damage		外层计算的伤害值
	 * @param pt			运行的时间点
	 * @return				buff之后的伤害值（有可能有变化，也可能没变化）<br>
	 * 
	 * 注意：这里只返回伤害值，除HP以外其余的buff效果，例如增加SP等，直接在run()内进行处理
	 * 
	 * 思考：
	 * 		如果有3个buf，一个是把所有的伤害变成1，还有一个是把所有的伤害增加20，第三个是把受到的伤害变成加血
	 * 		如果有2个buf，一个是把所有的伤害变成1，还有一个是中毒效果，发招前自己减血100
	 * 
	 * 目前没办法处理buff叠加的情况了
	 */
	public int run( int damage, BuffRunPoint pt ){
		
		int tempDamage = damage;//一个buf所产生的临时伤害值
		for( BuffBase buf : buffs ){
			
			if( buf.getBuffRunPoint() == pt && !buf.isRemove() ){
				tempDamage = buf.run( damage );

			}
		}		
		return tempDamage;
	}

	public static void main(String[] args) {
	
		List<BuffBase> list = new ArrayList<BuffBase>();
		for( int i = 0; i < 10; i++ ){
			BuffBase buf = new B001( null );
			list.add( buf );			
		}
		
		int i = 0;
		for( BuffBase buf : list ){
			if( i++ == 5 ){
				list.remove( i );
				list.remove( buf );
			}			
		}
	}
}
