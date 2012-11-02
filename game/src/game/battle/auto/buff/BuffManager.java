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

import game.battle.IBuff;

import java.util.ArrayList;
import java.util.List;

import com.lemon.ai.socket.packets.spiritgame.battle.AbstractBattle;
import com.lemon.ai.socket.packets.spiritgame.elite_staff.Fighter;

/**
 * 
 */
public class BuffManager {
	
	private List<IBuff>						buffs = new ArrayList<IBuff>();
	//private AbstractBattle						battle;
	//private Fighter 							fighter;//拥有此buff list 的战士，某些buff运算需要，例如“迷惑视听”
	
	/**
	 * @param fighter
	 */
	public BuffManager( Fighter fighter ) {
		super();
		this.fighter = fighter;
		//this.battle = battle;
	}
	
	
	/**
	 * 清除该战士所有的buff效果
	 */
	public void removeAllBuff( ){
//		for( BuffBase buf : buffs ){
//			if( !buf.remove )
//			{
//				buf.remove( battle );
//			}
//		}
		buffs.clear();
	}
	public boolean add( BuffBase buff ){
		
		//有可能需要考虑buff之间的兼容关系, 是一个很麻烦的高难度过程		
		buffs.add( buff );
		return false;
	}
	
	/**
	 * 根据时间点参数，运行相应的buff内容
	 * 
	 * @param pit		buff运行的时间点
	 * @param hp		不考虑Buff情况下将被扣除的damage
	 * 
	 * @return	
	 * 					经过buff之后，真正将被扣除的damage
	 * 
	 * 注意：这里只返回伤害值，除HP以外其余的buff效果，例如增加SP等，直接在run()内进行处理
	 */
	public float run( PointInTime pit, float damage ){
		float tempDamage = damage;//一个buf所产生的临时伤害值
		for( BuffBase buf : buffs ){
			
			if( buf.getPit() == pit && !buf.isRemove() ){
				tempDamage = buf.run( tempDamage, battle, fighter );				
			}
		}
		
		return tempDamage;
	}	
	
	/**
	 * @return the battle
	 */
	public AbstractBattle getBattle() {
		return battle;
	}


	/**
	 * @param battle the battle to set
	 */
	public void setBattle(AbstractBattle battle) {
		this.battle = battle;
	}


	public static void main(String[] args) {
		
		
		float n = 0;
		//for( int i = 0; i < 16777219; i++ ){
		long begin = System.nanoTime();
		for( int i = 0; i < 10000; i++ ){
			n += i;
		}
		System.out.println( (System.nanoTime() - begin ) / 1000000000f );
		System.out.println( n );
		
		int nn = 0;
		//for( int i = 0; i < 16777219; i++ ){
		begin = System.nanoTime();
		for( int i = 0; i < 10000; i++ ){
			nn += i;
		}
		System.out.println( (System.nanoTime() - begin ) / 1000000000f );
		System.out.println( nn );
	}

}
