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
			String sql = "SELECT templet_id,count,prop_id " +
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
		BaseGrid grid = new BaseGrid( templet, propId, count );
		return grid;
	}

}
