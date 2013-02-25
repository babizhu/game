package game.award;

import game.ITransformStream;

import java.nio.ByteBuffer;

public class AwardInfo implements ITransformStream {

	private AwardContent 		award;
	private short				propId;
	private int					number;
	
	/**
	 * 道具奖励
	 * @param award
	 * @param propId
	 * @param number
	 */
	public AwardInfo(AwardContent award, short propId, int number) {
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
	public AwardInfo(AwardContent award, int number) {
		super();
		this.award = award;
		this.number = number;
	}


	public AwardContent getAward() {
		return award;
	}
	public void setAward(AwardContent award) {
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
	
	

	@Override
	public void buildTransformStream(ByteBuffer buf) {
		buf.put( award.toNumber() );
		if( award == AwardContent.PROP ){
			buf.putShort( propId );
		}
		buf.putInt( number );
		
	}
	
}
