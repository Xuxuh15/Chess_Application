package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChessServer {
	
	  private ServerSocket server;
	  private int id;
	  private String buf[] = { "The Object class also has support for wait",
	      "If the timer has expired, the thread continues", "This call can cause some overhead in programs",
	      "Notify signals a waiting thread to wake up", "Wait blocks the thread and releases the lock" };

	  public ChessServer(int portNum) {
	    try {
	    	this.server = new ServerSocket(portNum); 
	    	this.id = 0; 
	    	System.out.println("Server is running....."); 
	    }
	    catch(IOException e) {
	    	e.printStackTrace();
	    	System.out.println("Initialization Error: Error in initializing server. Exiting program"); 
	    	System.exit(1);
	    }
	  }
	  
	  public ServerSocket getServer() {
		  return this.server; 
	  }
	  
	  public int getID() {
		  return this.id; 
	  }
	  
	  public void incrementId() {
		  this.id++; 
	  }

	 
	  public static void main(String args[]) throws IOException {
		
		  
		Socket sockP1 = null; 
		Socket sockP2 = null; 
	    try {
	      if (args.length != 1) {
	        System.out.println("Usage: java ChessServer --args=<port num>");
	        System.exit(0);
	      }
	      
	      int portNo = Integer.parseInt(args[0]);
	      if (portNo <= 1024)
	        portNo = 8888;
	      
	      ChessServer server = new ChessServer(portNo); 
	      
	      while (true) {
	        System.out.println("Threaded server waiting for connects on port " + portNo);
	        sockP1 = server.getServer().accept(); //blocking wait
	        server.incrementId();
	        System.out.println("Threaded server connected to client-" + server.getID());
	        sockP2 = server.getServer().accept(); //blocking wait
	        server.incrementId();
	        System.out.println("Threaded server connected to client-" + server.getID());
	        
	       
	        
	        // create thread
	        Thread serverThread = new Thread(new GameSession(sockP1, sockP2)); 
	        // run thread and don't care about managing it
	        serverThread.start(); 
	      }
	      
	      
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      if (sockP1 != null) sockP1.close();
	      if(sockP2 != null) sockP2.close(); 
	    }
	  }
	
	
	
	

}
