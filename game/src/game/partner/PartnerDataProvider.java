package game.partner;


import game.util.GameUtil;

import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.PreparedStatement;

import util.db.DatabaseUtil;

/**
 * 和数据库打交道的道具类，包括，装备，宝石，材料
 * 单体
 * 
 * @author liukun 2012-8-20 下午05:53:59
 */
public class PartnerDataProvider {
	private final static Logger 		logger = LoggerFactory.getLogger(PartnerDataProvider.class);
	private static PartnerDataProvider 	instance = new PartnerDataProvider();
	private static AtomicLong			maxID;
	static{
		maxID = new AtomicLong( GameUtil.buildIdWithDistrict( DatabaseUtil.getMaxId( "partner_base", "id" ) ) );
	}
	public static  PartnerDataProvider getInstance(){
		return instance;
	}
	private PartnerDataProvider(){
	}

	public void add(PartnerBase pbo) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "insert into partner_base (id, name, level, equipment, byte) "
					+ "values (?, ?, ?, ?, ?)";
			ps = DbUtils.getPst(sql);
			ps.setLong(1, pbo.getId());
			ps.setString(2, pbo.getName());
			ps.setInt(3, pbo.getLevel());
			ps.setString(4, pbo.getEquipment());
			ps.setBoolean(5, pbo.getByte());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.free(ps, rs);
		}
	}
		
	public void delete(long id) { 
		ResultSet rs = null; 
		PreparedStatement ps = null; 
		try { 
			String sql = "delete from partner_base where id = ?";
			ps = DbUtils.getPst(sql);
			ps.setLong(1, id);
			
			ps.executeUpdate(); 
 		} catch (SQLException e) {
			e.printStackTrace(); 
		} finally { 
			DbUtils.free(ps, rs); 
		} 
	} 

	public void update(PartnerBase pbo) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "update partner_base set name = ?, level = ?, equipment = ?, byte = ? where id = ?";
			ps = DbUtils.getPst(sql);
			ps.setString(1, pbo.getName());
			ps.setInt(2, pbo.getLevel());
			ps.setString(3, pbo.getEquipment());
			ps.setBoolean(4, pbo.getByte());
			ps.setLong(5, pbo.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.free(ps, rs);
		}
	}
		
	public PartnerBase get(long id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PartnerBase pbo = null;
		try {
			String sql = "select * from partner_base where id = ?";
			ps = DbUtils.getPst(sql);
			ps.setLong(1, id);
			
			rs = ps.executeQuery();
			while (rs.next()) {
			pbo = mapping(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.free(ps, rs);
		}
		return pbo;
	}
		
	public Map<Long, PartnerBase> getAll( String uname ) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PartnerBase> pbos = null;
		try {
			pbos = new ArrayList<PartnerBase>();
			String sql = "select * from partner_base limit ?, ?";
			ps = DbUtils.getPst(sql);
			ps.setInt(1, (pageNo - 1) * pageSize);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				PartnerBase pbo = new PartnerBase();
				pbo = mapping(rs);
				pbos.add(pbo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.free(ps, rs);
		}
		return pbos;
	}
	
	public List<PartnerBase> getAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PartnerBase> pbos = null;
		try {
			pbos = new ArrayList<PartnerBase>();
			String sql = "select * from partner_base";
			ps = DbUtils.getPst(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				PartnerBase pbo = new PartnerBase();
				pbo = mapping(rs);
				pbos.add(pbo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.free(ps, rs);
		}
		return pbos;
	}
	
	public int getCount() { 
		PreparedStatement ps = null; 
 		ResultSet rs = null;
		int count = 0; 
 		try { 
			String sql = "select count(*) from partner_base"; 
			ps = DbUtils.getPst(sql);
			rs = ps.executeQuery(); 
			while (rs.next()) { 
				count =  rs.getInt(1); 
			} 
		} catch (SQLException e) { 
 			e.printStackTrace();
		} finally { 
 			DbUtils.free(ps, rs);
		} 
 		return count; 
 	} 
 
	private PartnerBase mapping(ResultSet rs) throws SQLException {
		PartnerBase pbo = new PartnerBase();
		pbo.setId(rs.getLong("id"));
		pbo.setName(rs.getString("name"));
		pbo.setLevel(rs.getInt("level"));
		pbo.setEquipment(rs.getString("equipment"));
		pbo.setByte(rs.getBoolean("byte"));
		return pbo;
	}

	
	public static void main(String[] args) {
	}
}
