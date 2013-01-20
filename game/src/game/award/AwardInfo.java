package game.award;

import java.nio.ByteBuffer;

public class AwardInfo {

	private AwardType 		award;
	private short			propId;
	private int				number;
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
