package game.prop;


import game.prop.cfg.PropTempletCfg;
import game.prop.equipment.Equipment;
import game.prop.templet.BasePropTemplet;
import game.prop.templet.EquipmentTemplet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ErrorCode;
import util.db.DatabaseUtil;

/**
 * 和数据库打交道的道具类，包括，装备，宝石，材料
 * 单体
 * 
 * @author liukun 2012-8-20 下午05:53:59
 */
public class PropDataProvider {
	private final static Logger 		logger = LoggerFactory.getLogger(PropDataProvider.class);
	private static PropDataProvider 	instance = new PropDataProvider();
	private static AtomicLong			maxID;
	static{
		maxID = new AtomicLong( DatabaseUtil.getMaxId( "equipment_base", "id" ) );
	}
	public static  PropDataProvider getInstance(){
		return instance;
	}
	private PropDataProvider(){
	}
	
	
	/**
	 * 从数据库获取所有材料信息
	 * @param uname
	 * @return
	 */
	public Map<Short, Integer> getAllStuffs(String uname) {
		return null;
	}
	
	public ErrorCode changeStuff( PropUnit stuff, String uname, boolean isNew ){
		if( stuff.getCount() == 0 ){
			//TODO:删除此材料
		}
		else{
			addStuff(stuff, uname, isNew);
		}
		return ErrorCode.SUCCESS;
	}
	
	public ErrorCode addStuff( PropUnit stuff, String uname, boolean isNew ){
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;	
		String sql;
		if( isNew ){
			sql = "insert into stuff_base ( count,uname, typeid ) "
					+ "values (?, ?, ? )";
		}
		else{
			sql = "update equipment_base set count = ? where uname = ? and typeid=? ) ";
		}
		int i = 1;
		try {
			pst = con.prepareStatement( sql );
			pst.setInt( i++, stuff.getCount() );
			pst.setString( i++, uname );
			pst.setShort( i++, stuff.getTemplet().getTempletId() );
			pst.executeUpdate();
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
			return ErrorCode.DB_ERROR;
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		return ErrorCode.SUCCESS;
	}
	
	public Equipment addEquipment( EquipmentTemplet templet, String uname ) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;			
		Equipment equipment = new Equipment( templet, maxID.incrementAndGet() );
		String sql = "insert into equipment_base (id, uname, level, gem, typeid) "
				+ "values (?, ?, ?, ?,?)";
		int i = 1;
		try {
			pst = con.prepareStatement( sql );
			pst.setLong( i++, equipment.getId() );
			pst.setString( i++, uname );
			pst.setShort( i++, equipment.getLevel() );
			pst.setString( i++, equipment.getGemStr() );
			pst.setString( i++, equipment.getGemStr() );
			pst.setShort( i++, templet.getTempletId() );
			pst.executeUpdate();
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
			return null;
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		return equipment;
//		return ErrorCode.SUCCESS;
	}
		
	/**
	 * 注意外层权限控制，不要删除到别人的道具了
	 * @param equipment
	 */
	public ErrorCode removeEquipment( Equipment equipment ) { 
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		String sql = "delete from equipment_base where id = ?";
		try { 
			pst = con.prepareStatement( sql );
			pst.setLong(1, equipment.getId() );
			
			pst.executeUpdate(); 
 		} catch (SQLException e) {
 			logger.debug( e.getLocalizedMessage(), e );
			return ErrorCode.DB_ERROR;
		} finally { 
			DatabaseUtil.close( null, pst, con );
		}
		return ErrorCode.SUCCESS;
	} 

	/**
	 * 注意外层权限控制，不要修改到别人的道具了
	 * @param equipment
	 */
	public void updateEquipment( Equipment equipment ) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		String sql = "update equipment_base set level = ?, gem = ? where id = ?";
		try {
			pst = con.prepareStatement( sql );
			pst.setShort( 1, equipment.getLevel() );
			pst.setString( 2, equipment.getGemStr() );
			pst.setLong( 3, equipment.getId() );
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
	}
	
	/**
	 * 从数据库获取某个玩家的所有装备信息
	 * @param uname
	 * @return
	 */
	public Map<Long,Equipment> getAllEquipments( String uname ) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map<Long,Equipment> equipments = new HashMap<Long, Equipment>();
		
		String sql = "select * from equipment_base where uname=?";
		
		try {
			pst = con.prepareStatement( sql );
			pst.setString( 1, uname );
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Equipment e = mappingEquipment( rs );
				equipments.put( e.getId(), e );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		return equipments;
	}
	 
	private Equipment mappingEquipment( ResultSet rs ) throws SQLException {
		long id = rs.getLong( "id" );
		short level = rs.getShort("level");
		BasePropTemplet templet = PropTempletCfg.getTempletById( rs.getShort("typeId") );
		Equipment equipment  = new Equipment( templet, id );
		
		equipment.setLevel( level );
		equipment.setGem( rs.getString( "gem" ) );
		return equipment;
	}
}
