/**
 * 
 */
package bootstrap;
import game.prop.cfg.PropTempletCfg;
import game.task.cfg.TaskTempletCfg;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import javax.management.JMException;

import net.GameHandler;

import org.xsocket.connection.ConnectionUtils;
import org.xsocket.connection.IConnection.FlushMode;
import org.xsocket.connection.IHandler;
import org.xsocket.connection.Server;

import util.BaseUtil;
import util.SystemTimer;
import define.SystemCfg;

/**
 * @author liukun
 * 2012-8-15
 */
public class GameServer extends Server{

	/**
	 * @param address
	 * @param port
	 * @param handler
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public GameServer( InetAddress address, int port, IHandler handler ) throws UnknownHostException, IOException {
		super(address, port, handler);
		setFlushmode( FlushMode.ASYNC );
		setIdleTimeoutMillis( 1000000000 );
	}

	/**
	 * 初始化系统配置文件
	 */
	private void readCfg(){
		PropTempletCfg.init();
		TaskTempletCfg.init();
	}
	public static void main(String[] args) throws IOException, JMException {
		
		System.out.println("编码集= "+System.getProperty("file.encoding"));
		System.out.println("编码集1= "+Charset.defaultCharset() );

		System.out.println( BaseUtil.secondsToDateStr( SystemTimer.currentTimeSecond() ) + " server start now..." );
        System.out.println( BaseUtil.secondsToDateStr( SystemTimer.currentTimeSecond() ) + " game version: " + SystemCfg.VERSION );        
		
        
        InetAddress address = InetAddress.getByName( "localhost" );
		
		GameServer server = new GameServer( address, SystemCfg.PORT, new GameHandler() );
		server.readCfg();

		
		server.start();
		ConnectionUtils.registerMBean( server );   
		
        //System.out.println( "日志: " + server.getStartUpLogMessage() );
//		Map<String, Class> maps = server.getOptions();   

//         if( maps != null ){   
//                
//             for (Entry<String, Class> entry : maps.entrySet()) {   
//                 System.out.println("key= "+entry.getKey()+" value ="+entry.getValue().getName());   
//             }   
//         }           
         
	}
}
