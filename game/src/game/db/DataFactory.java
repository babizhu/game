package game.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import org.apache.log4j.Logger;


/**
 * 类说明：数据工场
 * @author 严欢	2010-9-4 下午09:47:30
 *
 */
public class DataFactory {
	
    private int errorNum=0;
	//Logger loger = null;
	List<Connection> conPool = new ArrayList<Connection>();
	int poolSize;
	int pointer = 0;
	
	long timeIntervalReConnect = 2 * 1000;
	private String driverName;
	private String url;
	private String user;
	private String password;

	/**
	 * 方法说明：构造方�?
	 * @author 严欢	2010-9-4 下午11:33:50
	 * @param driverName	连接驱动
	 * @param url			数据库地�?
	 * @param user			数据库用�?
	 * @param password		数据库密�?
	 * @param poolSize		连接池大�?
	 * @param timeIntervalReConnect		数据库断线重连时间间隔（毫秒�?
	 */
	public DataFactory(String driverName, String url, String user, String password, int poolSize, long timeIntervalReConnect){
		this.driverName = driverName;
		this.url = url;
		this.user = user;
		this.password = password;
		this.poolSize = poolSize;
		this.timeIntervalReConnect = timeIntervalReConnect;
		setPoolSize(poolSize);
		
		new Thread(new DataFactoryWatchDog()).start();
		
//		loger = Logger.getLogger(DataFactory.class);
//		loger.info("连接�?+url+"初始�?..连接�?"+poolSize+")");
	}
	
	/**
	 * 方法说明：从连接池中取一个可用的Connection
	 * @author 严欢	2010-9-4 下午11:34:41
	 * @return
	 */
	private  Connection getConnection(){
		Connection con = null;
		
		while(con == null){
			try{
				synchronized (new Integer(pointer)) {
					con = conPool.get(pointer);
					pointer = (pointer + 1) % poolSize;
				}
				
				if(!checkConAvailable(con)){
					con = null;
					sleep(1);
				}
			}catch (Exception e) {
				//loger.error("getConnection 异常", e);
				con = null;
				sleep(1);
			}
		}
		
		return con;
	}
	
	/**
	 * 方法说明：检查连接的有效�?
	 * @author 严欢	2010-9-4 下午10:17:55
	 * @param con
	 * @return	true有效		false无效
	 * @throws SQLException
	 */
	private boolean checkConAvailable(Connection con) throws SQLException{
		boolean result = false;
		if(con == null){
			result = false;
		}else{
			if(con.isClosed()){
				result = false;
			}else{
				result = true;
			}
		}
		return result;
	}

	public Connection getConData(){
	    Connection con = this.getConnection();
	    return con;
	}
	
	
	/**
	 * 方法说明：获取一个有效的PreparedStatement
	 * @author 严欢	2010-9-4 下午11:35:21
	 * @param sql
	 * @return
	 */
	public PreparedStatement getPst(String sql){
		PreparedStatement pst = null;
		Connection con = null;
		while(pst == null){
			try{
				con = this.getConnection();
				pst = con.prepareStatement(sql);
			}catch (Exception e) {
		    	errorNum++;
		    	if(errorNum>=70)clearPool();
				//loger.error("", e);
				pst = null;
				sleep(1);
			}
		}
		return pst;
	}
	
	public PreparedStatement getPst(String sql,int x, int y){
		PreparedStatement pst = null;
	    Connection con = null;
		while(pst == null){
			try{
				con = this.getConnection();
				pst = con.prepareStatement(sql, x, y);
			}catch (Exception e) {
				//loger.error("", e);
				pst = null;
				sleep(1);
			}
		}
		return pst;
	}
	
	public PreparedStatement getPst2(String sql){
		Connection con = null;
		PreparedStatement pst = null;
		
		while(pst == null){
			try{
				con = this.getConnection();
				pst = con.prepareStatement(sql);
			}catch (Exception e) {
			    	errorNum++;
			    	if(errorNum>=70)clearPool();
				//loger.error("", e);
				pst = null;
				sleep(1);
			}
		}
		return pst;
	}
	
	public PreparedStatement getPst2(String Sql,int x, int y){
	    Connection con = null;
		PreparedStatement pst = null;
		
		while(pst == null){
			try{
				con = this.getConnection();
				pst = con.prepareStatement(Sql, x, y);
			}catch (Exception e) {
				//loger.error("", e);
				pst = null;
				sleep(1);
			}
		}
		return pst;
	}
	
	
	public CallableStatement getPcl(String Sql,int x ,int y){
	    Connection con = null;
	    CallableStatement pcl = null;
		
		while(pcl == null){
			try{
				con = this.getConnection();
				pcl = con.prepareCall(Sql, x, y);
			}catch (Exception e) {
				//loger.error("", e);
				pcl = null;
				sleep(1);
			}
		}
		return pcl;
	}
	
	/**
	 * 方法说明：将Connection加入到连接池�?
	 * @author 严欢	2010-9-4 下午11:35:47
	 * @param con
	 */
	private void addConnectionToPool(Connection con) {
		boolean result = false;
		try {
			result = checkConAvailable(con);
		} catch (SQLException e) {
			//loger.error("", e);
		}
		if(result){
			this.conPool.add(con);
//			System.err.println(con);
		}
	}

	/**
	 * 方法说明：重置连接池大小
	 * @author 严欢	2010-9-4 下午11:36:10
	 * @param size
	 * @return
	 */
	private synchronized boolean setPoolSize(int size){
		boolean result = false;
		
		int curPoolSize = conPool.size();
		if(curPoolSize == size){
			result = true;
		}else if(curPoolSize < size){//由小变大
			while(curPoolSize < size){
				addConnectionToPool(getConFromPhysical());
				curPoolSize++;
			}
		}else{
		}
		
		if(conPool.size() == size){
			result = true;
		}
		return result;
	}
	
	/**
	 * 方法说明：取得一个物理的数据库连�?
	 * @author 严欢	2010-9-4 下午11:36:28
	 * @return
	 */
	private Connection getConFromPhysical(){
		Connection con = null;
		
		while(con == null){
			try {
				Class.forName(this.driverName);
				con = DriverManager.getConnection(this.url,this.user,this.password);
			} catch (ClassNotFoundException e) {
				//loger.error("", e);
			} catch (SQLException e) {
//				if(loger != null){
//					loger.error("", e);
//				}else{
//					e.printStackTrace();
//				}
				
				con = null;
				sleep(1);
			}
		}
		return con;
	}
	
	
	/**
	 * 通知关闭 prepareStatement
	 */
	public void closePst(PreparedStatement pst){
		try {
			pst.close();
		} catch (Exception e) {
			//BaseDefinition.logger.warn("closePst error", e);
		}	
	}
	
	public void closePst2(PreparedStatement pst){
		try {
			pst.close();
		} catch (Exception e) {
			//BaseDefinition.logger.warn("closePst error", e);
		}	
	}
	
	/**
	 * 通知关闭 CallableStatement
	 */
	public void closePcl(CallableStatement pcl){
		try {
			pcl.close();
		} catch (Exception e) {
			//BaseDefinition.logger.warn("closePcl error", e);
		}	
	}
	
	/**
	 * 方法说明：获取连接池的空闲数�?
	 * @author 严欢	2010-9-4 下午11:37:05
	 * @return
	 */
	public int getPoolFreeSize(){
		return this.conPool.size();
	}
	
	/**
	 * 方法说明：获取连接池的中的大�?
	 * @author 严欢	2010-9-4 下午11:37:36
	 * @return
	 */
	public int getPoolTotalSize(){
		return this.poolSize;
	}
	
	/**
	 * 方法说明：休眠一段时�?
	 * @author 严欢	2010-9-4 下午11:38:07
	 * @param millis
	 */
	private void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void clearPool(){
	    System.out.println("数据异常严重，重�?");
	    conPool.clear();
	    setPoolSize(poolSize);
	    errorNum=0;
	}
	
	
	
	
	class DataFactoryWatchDog implements Runnable{

		
		@Override
		public void run() {
			while(true){
				try{
					sleep(timeIntervalReConnect);
					
					removeClosedConnection();
					
					setPoolSize(poolSize);
					//System.out.println("[注意]当前pstmap缓存的sql数量�?+pstmap.size());
				}catch (Exception e) {
//					if(loger != null){
//						loger.error("", e);
//					}else{
//						e.printStackTrace();
//					}
				}
			}
		}

		/**
		 * 方法说明：移除连接池中关闭的链接
		 * @author 严欢	2010-9-5 上午12:16:34
		 */
		private void removeClosedConnection() {
			Iterator<Connection> iter = conPool.iterator();
			while (iter.hasNext()) {
				try {
					Connection con = iter.next();
					if (con == null) {
						iter.remove();
					}else{
						if(con.isClosed()){
							iter.remove();
						}
					}
				} catch (SQLException e) {
				}

				sleep(1);
			}
		}
		
	}
	
}


