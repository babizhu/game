/**
 * 
 */
package net;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Date;

import javax.management.JMException;

import org.xsocket.connection.ConnectionUtils;
import org.xsocket.connection.IHandler;
import org.xsocket.connection.Server;
import org.xsocket.connection.IConnection.FlushMode;

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

 
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, JMException {
		
		System.out.println("编码集= "+System.getProperty("file.encoding"));
		System.out.println("编码集1= "+Charset.defaultCharset() );

		System.out.println( new Date().toLocaleString() + " server start now..." );
        System.out.println( new Date().toLocaleString() + " game version: " + SystemCfg.VERSION );        
		
        InetAddress address = InetAddress.getByName( "localhost" );
		
		GameServer server = new GameServer( address, SystemCfg.PORT, new GameHandler() );
		
		ConnectionUtils.registerMBean( server );   

		
		server.start();
		
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
