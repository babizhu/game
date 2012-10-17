package game.bag;

import game.prop.cfg.PropTempletCfg;
import game.prop.templet.BasePropTemplet;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.IllegalAddException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.ErrorCode;
import util.db.DatabaseUtil;

/**
 * 背包相关的数据库方法
 * @author liukun
 * 2012-10-11 下午04:00:40
 */
class BagDataProvider {
	
	private final static Logger logger = LoggerFactory.getLogger(BagDataProvider.class);
	private static BagDataProvider instance = new BagDataProvider();
	static  BagDataProvider getInstance(){
		return instance;
	}
	private BagDataProvider(){
	}
	
	/**
	 * 通过用户名获取玩家背包内所有的格子
	 * @param uname
	 * @return
	 */
	List<BaseGrid> getAllGridByUser( String uname ){
		List<BaseGrid> list = new ArrayList<BaseGrid>();
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT templet_id,count,prop_id,position " +
					"from bag_grid " +
					"where uname=?";
			pst = con.prepareStatement( sql );
			pst.setString( 1, uname );
			rs = pst.executeQuery();

			while( rs.next() ){
				BaseGrid g = mapping(rs);
				list.add(g);
			}
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
		}
		finally{
			DatabaseUtil.close( rs, pst, con );
		}
		return list;
	}
	
	private BaseGrid mapping(ResultSet rs) throws SQLException {
		
		short tid = rs.getShort( "templet_id" );
		BasePropTemplet templet = PropTempletCfg.getTempletById( tid );
		if( templet == null ){
			throw new IllegalAddException( "道具模板不存在 " + tid );
		}
		short count = rs.getShort( "count" );
		long propId = rs.getLong( "prop_Id" );
		short position = rs.getShort( "position" );
		BaseGrid grid = new BaseGrid( templet, propId, count, position );
		return grid;
	}
	
	/**
	 * 向背包内添加一个格子
	 * @param task		新格子
	 * @param uname		玩家名<br>
	 * 
	 * @return
	 */
	ErrorCode create( BaseGrid grid, String uname ) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;								  
		String sql = "insert into bag_grid (uname, bag,templet_id,count,prop_id,position) "
			+ "values (?, ?, ?, ?, ?, ?)";
		int	i = 1;
		try {
			pst = con.prepareStatement( sql );
	
			pst.setString( i++, uname );
			pst.setByte( i++, (byte) 1 );//bag
			pst.setShort( i++, grid.getTemplet().getTempletId() );
			pst.setShort( i++, grid.getCount() );
			pst.setLong( i++, grid.getPropId() );
			pst.setShort( i++, grid.getPosition() );
			
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
	 * 修改玩家任务信息，针对某些改动比较频繁且重要的字段，可考虑专门做一个语法糖，优化的事情放到以后再说
	 * @param task		任务
	 * @param uname		玩家名
	 * @return
	 * 		DB_ERROR
	 */
	ErrorCode update( BaseGrid grid, String uname ) {
		
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement pst = null;	
		String sql = "update bag_grid set " +
				"count = ?, " +
				"templet_id = ?, " +
				"prop_id = ?, " +
				"where position = ? and uname = ?";
		
		int	i = 1;
		try {
			pst = con.prepareStatement( sql );
			
			pst.setShort( i++, grid.getCount() );
			pst.setShort( i++, grid.getTemplet().getTempletId() );
			pst.setLong( i++, grid.getPropId() );
			pst.setShort( i++, grid.getPosition() );
			pst.setString( i++, uname );
			
			pst.executeUpdate();
		} catch (SQLException e) {
			logger.debug( e.getLocalizedMessage(), e );
			return ErrorCode.DB_ERROR;
		} finally {
			DatabaseUtil.close( null, pst, con );
		}
		return ErrorCode.SUCCESS;
	}


}
