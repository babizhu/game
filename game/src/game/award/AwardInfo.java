package game.award;

import java.nio.ByteBuffer;

public class AwardInfo {

	private AwardType 		award;
	private short			propId;
	private int				number;
	
	/**
	 * 道具奖励
	 * @param award
	 * @param propId
	 * @param number
	 */
	public AwardInfo(AwardType award, short propId, int number) {
		super();
		this.award = award;
		this.propId = propId;
		this.number = number;
	}
	
	/**
	 * 不包括道具的奖励
	 * @param award
	 * @param number
	 */
	public AwardInfo(AwardType award, int number) {
		super();
		this.award = award;
		this.number = number;
	}


	public AwardType getAward() {
		return award;
	}
	public void setAward(AwardType award) {
		this.award = award;
	}
	public short getPropId() {
		return propId;
	}
	public void setPropId(short propId) {
		this.propId = propId;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void buildBytes( ByteBuffer buffer ){
		buffer.put( award.toNumber() );
		if( award == AwardType.PROP ){
			buffer.putShort( propId );
		}
		buffer.putInt( number );
	}
	
}
