package game.partner;


import game.partner.cfg.PartnerTempletCfg;
import game.util.GameUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import user.UserInfo;
import util.ErrorCode;
import util.db.DatabaseUtil;

/**
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

	public ErrorCode add( PartnerBase pbo, String uname ) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;	
		String sql = "insert into partner_base (id, name, level, equipment, position, uname,typeid ) "
					+ "values (?, ?, ?, ?, ?, ?, ?)";
		long id = maxID.incrementAndGet();
		pbo.setId( id );
		try {
			pst = con.prepareStatement( sql );
			pst.setLong( 1, pbo.getId() );
			pst.setString( 2, pbo.getName() );
			pst.setInt( 3, pbo.getLevel() );
			pst.setString( 4, pbo.getEquipmentToStr() );
			pst.setByte( 5, pbo.getPosition() );
			pst.setString( 6, uname );
			pst.setShort( 6, pbo.getTemplet().getTempletId() );
			pst.executeUpdate();
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
			return ErrorCode.DB_ERROR;
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		return ErrorCode.SUCCESS;
	}
		
	public ErrorCode remove( long id ) { 
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		String sql = "delete from partner_base where id = ?";
		try { 
			pst = con.prepareStatement( sql );
			pst.setLong( 1, id );
			
			pst.executeUpdate(); 
			
 		} catch (SQLException e) {
 			logger.debug( e.getLocalizedMessage(), e );
			return ErrorCode.DB_ERROR;
		} finally { 
			DatabaseUtil.close( null, pst, con );
		}
		return ErrorCode.SUCCESS;
	} 

	public void update( PartnerBase pbo) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		String sql = "update partner_base set name = ?, level = ?, equipment = ?, byte = ? where id = ?";
		try {
			pst = con.prepareStatement( sql );
			pst.setString( 1, pbo.getName());
			pst.setInt( 2, pbo.getLevel());
			pst.setString( 3, pbo.getEquipmentToStr() );
			pst.setByte( 4, pbo.getPosition() );
			pst.setLong( 5, pbo.getId() );
			pst.executeUpdate();
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
	}
		
	
		
	public List<PartnerBase> getAll( UserInfo user ) {
		String uname = user.getName();
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<PartnerBase> list = new ArrayList<PartnerBase>();
		String sql = "select * from partner_base where uname = ?";
		try {
			pst = con.prepareStatement( sql );
			pst.setString( 1, uname );
			rs = pst.executeQuery();
			while (rs.next()) {
				PartnerBase pbo = mapping( rs, user );
				list.add( pbo );
			}
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		return list;
	}
	

 
	private PartnerBase mapping(ResultSet rs, UserInfo user) throws SQLException {
		PartnerTemplet t = PartnerTempletCfg.getTempletById( rs.getShort( "typeid" ) );
		PartnerBase pbo = new PartnerBase( t );
		pbo.setId( rs.getLong("id") );
		pbo.setName( rs.getString( "name") );
		pbo.setLevel( rs.getShort("level") );
		pbo.setEquipmentFromStr( rs.getString("equipment"), user );
		pbo.setPosition( rs.getByte("position"));
		return pbo;
	}

	
	public static void main(String[] args) {
	}
}
