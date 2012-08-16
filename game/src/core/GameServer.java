/**
 * 
 */
package core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Map.Entry;

import org.xsocket.connection.IHandler;
import org.xsocket.connection.Server;

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
		// TODO Auto-generated constructor stub
	}
 
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException {
		InetAddress address = InetAddress.getByName( "localhost" );
		
		GameServer server = new GameServer( address, 8000, new GameHandler() );
		//server.setIdleTimeoutMillis( 10 );
		server.start();
		
		
		Map<String, Class> maps = server.getOptions();   
         if( maps != null ){   
                
             for (Entry<String, Class> entry : maps.entrySet()) {   
                 System.out.println("key= "+entry.getKey()+" value ="+entry.getValue().getName());   
             }   
         }   
         System.out.println( "日志: " + server.getStartUpLogMessage() );   
	}
}
