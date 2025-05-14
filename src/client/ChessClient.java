package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.json.JSONObject;

import helpers.Pair;
import logic.ChessConstants;

public class ChessClient {
	
	private Socket socket; 
	private ObjectInputStream in; 
	private ObjectOutputStream out; 
	private ChessConstants color; 
	
	
	ChessClient(Socket sock){
		this.socket = sock; 
		try {
			// get output channel
		      OutputStream outstream = sock.getOutputStream();
		      InputStream instream = sock.getInputStream(); 
		      // create an object output writer (Java only)
		      ObjectOutputStream out = new ObjectOutputStream(outstream);
		      ObjectInputStream in = new ObjectInputStream(instream); 
		      //store reference to object in/out streams
		      this.in = in; 
		      this.out = out; 
		}
		catch(IOException e) {
			e.printStackTrace(); 
		}
	}
	
	/**
	 * Returns a JSON request to send a move to server. 
	 * @param from the starting square
	 * @param to the destination square
	 * @return a JSON request object of type MOVE
	 */
	private JSONObject sendMove(Pair from, Pair to) {
		JSONObject req = new JSONObject(); 
		req.put("type", ChessConstants.MOVE); 
		String fromStr = from.row() + ":" + from.col(); 
		req.put("from", fromStr); 
		String toStr = to.row() + ":" + to.col(); 
		req.put("to", toStr); 
		return req; 
		
	}
	
	
	
	
	
	
	public static void main (String args[]) {
	    Socket sock = null;
	    String host = "localhost";
	    String message = "HI";
	    Integer number = 100;
	    ChessClient client; 
	    
	    
	    // works with no inputs or 1, 2 or 3
	    // no error handling for wrong arguments
	    if (args.length >= 1){ // host, if provided
	      host=args[0];
	    }
	    if (args.length >= 2){
	      message = args[1];
	    }
	    // user provided message and number, ignore 2 arguments 
	    if (args.length >= 3){ 
	      number = Integer.valueOf(args[2]);
	    }
	    
	    try {
	    	
	      // open the connection
	      sock = new Socket(host, 8888); // connect to host and socket on port 8888
	      
	      client = new ChessClient(sock);
	      
	      

	    
	    } catch (Exception e) {e.printStackTrace();}
	  }
	}
	
	


