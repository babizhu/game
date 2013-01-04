package game.battle.auto;

/**
 * 包含一次攻击的各种信息
 * @author liukun
 * 2012-12-21 下午05:23:52
 */
public class AttackInfo {
	
	/**
	 * 是否命中
	 */
	private static final int 			HIT = 2*2*2*2*2;//第五位
	
	/**
	 * 是否格挡，当前版本把格挡反击合二为一
	 */
	private static final int 			BLOCK = 2*2*2;//第三位
	
	/**
	 * 是否反击
	private static final int 			COUNTER_ATTACK = 2*2*2*2;
	 */
	
	/**
	 * 暴击倍数
	 */
	private static final int 			CRIT = 7;
	
	/**
	 * 获取攻击状态值, 该状态值包含了格挡信息, 命中信息, 暴击倍数
	 * 
	 * 0b00dcbaaa
	 * 
	 * aaa	三位来代表暴击倍数，也就是说最大为7倍暴击<br>
	 * b	是否格挡<br>
	 * c	是否反击<br>
	 * d	是否命中<br>
	 */
	private byte data = 0;
	
	/**
	 * 本次攻击的伤害值
	 */
	private int									damage;
	/**
	 * 获取网络传送用的raw数据的拷贝，就是一个byte
	 * @return
	 */
	byte getRawData(){
		return data;
	}
	
	boolean isHit(){
		return getBitValue( HIT );
	}
	
	void SetHit( boolean isHit ){
		setBitValue( HIT, isHit );
		
	}
	
	boolean isBlock(){
		return getBitValue( BLOCK );
	}
	
	void SetBlock( boolean isBlock ){
		setBitValue( BLOCK, isBlock );
	}
	
	/**
	 * 根据value的值把某一位置0或者置1
	 * @param flag		如果要对第五位进行处理，则应该传入2的5次方，位数要从左边并且从0开始数
	 * @param value		
	 * 					true	置为1
	 * 					false	置为0
	 */
	private void setBitValue( int flag, boolean value ){
		if( value ){
			data |= flag;
		}
		else{
			data &= ~flag;
		}
	}
	private boolean getBitValue( int flag ){
		return (data & flag) != 0;
	}
	
	/**
	 * 获取暴击率
	 * 1	无暴击
	 * @return
	 */
	byte getCrit(){
		return (byte) (data & CRIT );
	}
	
	/**
	 * 最大倍数不大于7倍
	 * @param crit
	 */
	void setCrit( int crit ){
		if( crit > 7 || crit < 1 ){
			throw new IllegalArgumentException( crit+"" );
		}
		data &= ~CRIT;
		data |= crit; 
	}
	
	
	int getDamage() {
		return damage;
	}

	void setDamage(int damage) {
		this.damage = damage;
	}

	public static void main(String[] args) {
		AttackInfo f = new AttackInfo();
		f.data = 8;
		
		System.out.println( f.getCrit() );
		System.err.println( f.isHit() );
		
		System.out.write( 2^4);
	}
}
