package game.prop;

import java.nio.ByteBuffer;

import game.ITransformStream;


public abstract class PropBase implements IProp, ITransformStream{

	
	/**
	 * 是否在背包里
	 */
	private boolean					inBag = true;
	
	
	public boolean isInBag() {
		return inBag;
	}

	public void setInBag(boolean inBag) {
		this.inBag = inBag;
	}
	
	@Override
	public void buildTransformStream( ByteBuffer buf ){
		buf.putShort( this.getTemplet().getId() );//模板id
		
	}
	
}
