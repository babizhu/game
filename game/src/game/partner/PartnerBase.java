package game.partner;

import game.ITransformStream;
import game.fighter.FighterBase;
import game.prop.ICalculateAddtion;
import game.prop.equipment.EquipmentBase;
import game.prop.equipment.EquipmentType;
import game.prop.templet.EquipmentTemplet;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import user.UserInfo;
import util.ErrorCode;

public class PartnerBase extends FighterBase implements ICalculateAddtion, ITransformStream{

	private final PartnerTemplet				templet;
	/**
	 * 伙伴唯一id
	 */
	private long								id;
	private String								name;
	private byte								position;
	private short								level;
	private Map<EquipmentType,EquipmentBase>	equipments = new HashMap<EquipmentType, EquipmentBase>();
	
	private final static Logger 				logger = LoggerFactory.getLogger(PartnerBase.class);
	
	
	public PartnerBase( PartnerTemplet templet ) {
		this.templet = templet;
	}

	public PartnerTemplet getTemplet() {
		return templet;
	}
	
	/**
	 * @link id
	 * @return
	 */
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte getPosition() {
		return position;
	}
	public void setPosition(byte position) {
		this.position = position;
	}
	public short getLevel() {
		return level;
	}
	public void setLevel(short level) {
		this.level = level;
	}
	
	/**
	 * 获取用逗号分割的装备id字符串用于数据库存储
	 * @return
	 */
	public String getEquipmentStr() {
		if( equipments.size() == 0 ){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for( EquipmentBase e : equipments.values() ){
			sb.append( e.getId() );
			sb.append( "," );
		}
		return sb.substring( 0, sb.length() - 1 );
	}

	public void setEquipmentFromStr( String str, UserInfo user ) {
		String[] estr = str.split( "," );
		for( String s : estr ){
			long id = Long.parseLong( s );
			EquipmentBase equipment = user.getPropManager().getEquipmentById( id );
			if( equipment != null ){
				equipment.setInBag( false );
				equipments.put( ((EquipmentTemplet) equipment.getTemplet()).getEquipmentType(), equipment );
			}
			else{
				logger.error( "玩家" + user.getName() + "道具" + id + "不存在！" );
			}
		}		
	}
	
	/**
	 * 如果存在旧装备，那么应该把此装备放入背包，这样的话如何与客户端通信呢，解决方法一般为：
	 * 1、主动刷新整个背包	：浪费资源
	 * 2、告知背包某个物品进入了背包：实现难度太高
	 * 
	 * @param newEquipment
	 * @return
	 */
	public ErrorCode dress( EquipmentBase newEquipment ){
		EquipmentTemplet t = (EquipmentTemplet) newEquipment.getTemplet();
		if( getLevel() < t.getRequiredLevel() ){
			return ErrorCode.PARTNER_LEVEL_NOT_ENOUGH;
		}
		
		EquipmentBase old = equipments.get( t.getEquipmentType() );
		if( old != null ){
			old.setInBag( true );			
		}
		
		equipments.put( t.getEquipmentType(), newEquipment );
		calcAddtion();
		return ErrorCode.SUCCESS;
	}

	@Override
	public void buildTransformStream(ByteBuffer buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calcAddtion() {
		// TODO Auto-generated method stub
		
	}
	
}
