/**
 * 
 */
package net;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.xsocket.connection.IHandler;
import org.xsocket.connection.Server;

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
	}

 
	public static void main(String[] args) throws IOException {

		InetAddress address = InetAddress.getByName( "localhost" );
		
		GameServer server = new GameServer( address, SystemCfg.PORT, new GameHandler() );
		//server.setIdleTimeoutMillis( 1000 );
		
		System.err.println( "server start now........" );
        System.out.println( "game version: " + SystemCfg.VERSION );        
		server.start();
		
        //System.out.println( "日志: " + server.getStartUpLogMessage() );
//		Map<String, Class> maps = server.getOptions();   
//
//         if( maps != null ){   
//                
//             for (Entry<String, Class> entry : maps.entrySet()) {   
//                 System.out.println("key= "+entry.getKey()+" value ="+entry.getValue().getName());   
//             }   
//         }           
         
	}
}
