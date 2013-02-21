package game.prop.equipment;

import java.nio.ByteBuffer;
import java.util.Arrays;

import util.ErrorCode;

import game.prop.templet.EquipmentTemplet;
import game.prop.templet.PropTempletBase;

public class Equipment extends EquipmentBase{

	private final EquipmentTemplet		templet;
	private Gem[]						gems = new Gem[0];

	public Equipment(PropTempletBase templet) {
		this.templet = (EquipmentTemplet) templet;
	}
	
	@Override
	public PropTempletBase getTemplet() {
		return templet;
	}
	
	/**
	 * 获取用逗号分割的宝石id字符串用于数据库存储
	 * @return
	 */
	public String getGemStr() {
		if( gems.length == 0 ){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for( Gem g : gems ){
			sb.append( g.getId() );
			sb.append( "," );
		}
		return sb.substring( 0, sb.length() - 1 );
	}

	public Gem[] getGem() {
		return gems;
	}

	public void setGem( String str ) {
		String[] arr = str.split( "," );
		gems = new Gem[arr.length];
		for( int i = 0; i < arr.length; i++ ){
//			Gem g = 
//			gems[i] = g;
			//TODO 这里要好好思考一下
		}
	}


	
	@Override
	public String toString() {
		return "Equipment " + super.toString() + ", gems=" + Arrays.toString(gems) + "]";
	}


	@Override
	public void buildTransformStream( ByteBuffer buffer ) {
		
	}

	@Override
	public void calcAddtion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ErrorCode levelUp() {
		// TODO Auto-generated method stub
		return null;
	}

}
