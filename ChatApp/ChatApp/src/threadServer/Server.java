package threadServer;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


public class Server {

	  public static void main(String[] args) throws IOException, SQLException{
	        int port = 9050;// open a port for listening
	        ServerSocket listener = new ServerSocket(port); // create a socket 
	        System.out.println("ThreadedSServer started on 9050");
	        try {
	            while (true) {
	                Socket socket = listener.accept(); 
	                new ThreadServer(socket).start();
	            }              
	        }
	        finally {
	            listener.close();
	        }
	    }

}

