package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.json.JSONObject;

import helpers.Pair;
import javafx.application.Application;
import logic.ChessConstants;
import logic.ChessLogic;
import ui.ChessUI;

/**
 * Handles communication between client and server
 */
public class ChessClient {
	
	private Socket socket; 
	private ObjectInputStream in; 
	private ObjectOutputStream out; 
	private char color; 
	
	
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
	public void sendMove(Pair from, Pair to) {
		System.out.println("<ChessClient.sendMove>: Sending from: " + from.toString() + " to: " + to.toString());
		JSONObject req = new JSONObject(); 
		req.put("type", ChessConstants.MOVE); 
		String fromStr =  from.row()  + ":" + ChessLogic.convertNumToLetter(from.col()); 
		req.put("from", fromStr); 
		String toStr = to.row() + ":" +  ChessLogic.convertNumToLetter(to.col()); 
		req.put("to", toStr); 
		try {
			out.writeObject(req.toString());
			out.flush();
		}
		catch(IOException e) {
			System.out.println("ChessClient<sendMove> Error: error sending request to server"); 
			e.printStackTrace();
			
		}
		
	}
	
	/*
	 * Return's the player's color. 
	 */
	public char getColor() {
		return this.color; 
	}
	
	/**
	 * Gets the assigned color from the server and sets color appropriately. 
	 */
	public void getAssignedColor() {
		JSONObject res; 
		
		try {
			String input = (String) this.in.readObject();
			res = new JSONObject(input); 
			if((int)res.getInt("type") == ChessConstants.ASSIGNMENT) {
				
				if(res.getInt("color") == (ChessConstants.WHITE)){
					this.color = ChessConstants.WHITE; 
				}
				else {
					this.color = ChessConstants.BLACK; 	
				}
				
				System.out.println("<ChessClient.getAssignedColor> Received assignedColor: " + Character.toString(this.color)); 
			}
			
		}
		catch(Exception e) {
			System.out.println("ChessClient<getAssignedColor> Error: error receiving response from server"); 
			e.printStackTrace(); 
		}
		
	}
	
	
	/**
	 * Consumes start response from server.
	 */
	public void waitForStart() {
		JSONObject res; 
		try {
			String in = (String)this.in.readObject(); 
			res = new JSONObject(in); 
			
			if(res.getInt("type") == ChessConstants.START) { 
				System.out.println("<ChessClient.waitForStart> Received start signal"); 
				return; 
			}
			
		}
		catch(Exception e) {
			System.out.println("ChessClient<waitForStart> Error: error receiving response from server"); 
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Checks whether it is the player's turn to move at the start of a game. 
	 * @return boolean whether it is the player's turn. 
	 */
	public boolean isMyTurn() { 
		JSONObject res; 
		boolean shouldPlay = false; 
		try {
			String in = (String)this.in.readObject(); 
			res = new JSONObject(in); 
			
			if(res.getInt("type") == ChessConstants.PLAY) {
				shouldPlay = res.getBoolean("shouldPlay"); 
				System.out.println("<ChessClient.isMyTurn> Received isMyTurn: " + this.isMyTurn()); 
			}
		}
		catch(Exception e) {
			System.out.println("ChessClient<isMyTurn> Error: error receiving response from server"); 
			e.printStackTrace();
		}
		return shouldPlay; 
	}
	
	/**
	 * Get update from server and return a Pair array where index 0 = source square and index 1 = destination square
	 * @return an array of 2 Pair objects [source,destination]
	 */
	public Pair[] getUpdate() {
		
		Pair[] update = new Pair[2]; 
		
		JSONObject res; 
		try {
			String in = (String)this.in.readObject(); 
			res = new JSONObject(in); 
			
			if(res.getInt("type") == ChessConstants.UPDATE) {
				String from = res.getString("from"); 
				update[0] = ChessLogic.getCoordinate(from); 
				String to = res.getString("to"); 
				update[1] = ChessLogic.getCoordinate(to);  
				System.out.println("<ChessClient.getUpdate> Received from: " + update[0].toString() + " to: " + update[1].toString()); 
			}
		}
		catch(Exception e) {
			System.out.println("ChessClient<getUpdate> Error: error receiving response from server"); 
			e.printStackTrace();
		}
		return update;
	}
	
	/**
	 * Checks whether the game is over. 
	 * @return boolean if clients should continue playing or not. 
	 */
	public boolean continuePlaying() {
		boolean shouldContinue = true; 
		JSONObject res; 
		try {
			String in = (String)this.in.readObject(); 
			res = new JSONObject(in); 
			System.out.println("<ChessClient.continuePlaying>  Received:  " + res.toString());
			
			if(!(res.getInt("type") == ChessConstants.CONTINUE)) {
				shouldContinue = false; 
				System.out.println("<ChessClient.continuePlaying>  Received:  " + res.getString("message"));
				
			}
		}
		catch(Exception e) {
			System.out.println("ChessClient<continuePlaying> Error: error receiving response from server"); 
			e.printStackTrace();
		}
		return shouldContinue; 
	} 
	
	
	/**
	 * Checks whether the last sent move was valid. 
	 * @return boolean if move was valid or not 
	 */
	public boolean wasValidMove() {
		boolean validMove = false; 
		JSONObject res; 
		try {
			String in = (String)this.in.readObject(); 
			res = new JSONObject(in); 
			
			if((res.getInt("type") == ChessConstants.WAS_VALID_MOVE)) {
				validMove = res.getBoolean("ok"); 
				System.out.println("ChessClient.wasValidMove> Received validMove: " + validMove); 
			}
		}
		catch(Exception e) {
			System.out.println("ChessClient<wasValidMove> Error: error receiving response from server"); 
			e.printStackTrace();
		}
		return validMove; 
	} 
	
	
	
	public static void main (String args[]) {
	    Socket sock = null;
	    String host = "localhost";
	    int port = 8888;
	    Integer number = 100;
	    ChessClient client; 
	    
	    
	    // works with no inputs or 1, 2 or 3
	    // no error handling for wrong arguments
	    if (args.length >= 1){ // host, if provided
	      host=args[0];
	    }
	    if (args.length >= 2){
	      port = Integer.parseInt(args[1]);
	    }
	    
	    try {
	    	
	      // open the connection
	      sock = new Socket(host, port); // connect to host and socket on port 8888
	      
	      client = new ChessClient(sock);
	      
	      //set client socket
	      ChessUI.setSocket(client);
	      
	      //launch the Chess Board
	      Application.launch(ChessUI.class, args);
	      
	      

	    
	    } catch (Exception e) {e.printStackTrace();}
	  }
	}
	
	


