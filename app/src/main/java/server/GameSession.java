package server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import helpers.Pair;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.InputMismatchException;

import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessLogic;
import logic.ChessPiece;
import logic.KingPiece;

public class GameSession implements Runnable {
	
	private Socket p1; 
	private Socket p2; 
	private ChessLogic logic; 
	private ChessBoard board; 
	private ChessPiece[] piecesWhite; 
	private ChessPiece[] piecesBlack; 
	

	
	public GameSession(Socket p1, Socket p2) {
		this.p1 = p1;
		this.p2 = p2; 
		this.logic = new ChessLogic(); 
		this.board = new ChessBoard(); 
	}
	
	
	public void setUpGame() {
		piecesBlack = ChessLogic.generatePieces(ChessConstants.BLACK); 
		piecesWhite = ChessLogic.generatePieces(ChessConstants.WHITE); 
		this.logic.setBlackPlayerChessArray(piecesBlack);
		this.logic.setWhitePlayerChessArray(piecesWhite);
		logic.setUpBoard(board);
		
	}
	
	public JSONObject assignColor(char color) {
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.ASSIGNMENT); 
		if(color == ChessConstants.WHITE) {
			res.put("color", ChessConstants.WHITE); 
		}
		else {
			res.put("color", ChessConstants.BLACK); 
		}
		
		return res; 
	}
	
	public JSONObject start() {
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.START); 
		return res; 
	}
	
	public JSONObject play(boolean shouldPlay) {
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.PLAY); 
		res.put("shouldPlay", shouldPlay); 
		return res; 
	}
	
	private JSONObject validMove() {
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.WAS_VALID_MOVE); 
		res.put("ok", true); 
		res.put("message", "Valid move received.");
		return res; 
	}
	
	private JSONObject updateBoard(Pair from, Pair to) {
		
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.UPDATE); 
		String fromStr = from.row() + ":" + ChessLogic.convertNumToLetter(from.col()); 
		res.put("from", fromStr); 
		String toStr = to.row() + ":" + ChessLogic.convertNumToLetter(to.col()); 
		res.put("to", toStr); 
		
		return res;
		
	}
	private JSONObject gameOver(char color) {
		
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.END); 
		String winner = ""; 
		if(color == ChessConstants.WHITE) {
			winner = "White"; 
		} 
		else {
			 winner = "Black"; 
		}
		res.put("message", "The winner is " + winner + " player."); 
		return res; 
		
	}
	
	private JSONObject continuePlaying() {
		
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.CONTINUE); 
		res.put("message", "Continue playing");
		return res; 
		
	}
	
	private JSONObject parseRequest(ObjectInputStream in) {
		JSONObject req; 
		try {
			String s = (String) in.readObject(); 
			req = new JSONObject(s); 
		}
		catch(IOException e) {
			req = new JSONObject(); 
			req.put("ok", false); 
			req.put("message", "Could not read input"); 
		}
		catch(ClassNotFoundException e) {
			req = new JSONObject(); 
			req.put("ok", false); 
			req.put("message", "Could not find class"); 
		}
		
		return req; 
		
	}
	
	private JSONObject invalidMove(String message) {
		
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.WAS_VALID_MOVE); 
		res.put("ok", false); 
		res.put("message", message); 
		return res; 
	}
	
	private JSONObject gameOverDueToTimeout(int playerColor) {
	    JSONObject res = new JSONObject();
	    res.put("type", ChessConstants.END);
	    res.put("reason", "timeout");
	    res.put("loser", playerColor);
	    return res;
	}

	private JSONObject gameOverDueToDisconnect(int playerColor) {
	    JSONObject res = new JSONObject();
	    res.put("type", ChessConstants.END);
	    res.put("reason", "disconnect");
	    res.put("loser", playerColor);
	    return res;
	}

	
	
	
	
	
	
	
	
	@Override 
	public void run() {
		
		ObjectInputStream p1In = null; 
		ObjectInputStream p2In = null; 
		ObjectOutputStream p1Out = null; 
		ObjectOutputStream p2Out = null; 
		
		boolean inSession = true; 
		boolean checkmate = false; 
		char currentPlayer = ChessConstants.WHITE; 
		boolean validMove = false;
		boolean hasMoved = false; 
		
		
		try {
			p1In = new ObjectInputStream(this.p1.getInputStream()); 
			p2In = new ObjectInputStream(this.p2.getInputStream()); 
			p1Out = new ObjectOutputStream(this.p1.getOutputStream()); 
			p2Out = new ObjectOutputStream(this.p2.getOutputStream()); 
			JSONObject res; 
			JSONObject req;
			
			// Assign white to player one
			res = this.assignColor(ChessConstants.WHITE); 
			
			p1Out.writeObject(res.toString());
			
			//Assign black to player two
			res = this.assignColor(ChessConstants.BLACK); 
			
			p2Out.writeObject(res.toString());
			
			//set up the chess board
			this.setUpGame(); 
			for(int i = 0; i < ChessConstants.ROWS; i++) {
				for(int j = 0; j < ChessConstants.COLUMNS; j++) {
					if(board.checkSpace(i, j) != null) {
						System.out.println(board.checkSpace(i, j).getToken() + " " + i + ":" + j); 
					}
				}
			}
			
			//indicate that the game has started
			res = this.start();  
			p1Out.writeObject(res.toString());
			p2Out.writeObject(res.toString());
			
			int count = 0; 
			while(inSession && !checkmate) {
				hasMoved = false; 
				
				ObjectInputStream currentPlayerIn; 
				ObjectOutputStream currentPlayerOut;  
				ObjectOutputStream waitingPlayerOut; 
				
				
				//determine which player's turn it is
				if(currentPlayer == ChessConstants.WHITE) {
					currentPlayerIn = p1In; 
					currentPlayerOut = p1Out;  
					waitingPlayerOut = p2Out; 
				}
				else {
					currentPlayerIn = p2In; 
					currentPlayerOut = p2Out; 
					waitingPlayerOut = p1Out; 
				}
				//tell white player that they should make a move
				res = this.play(true); 
				currentPlayerOut.writeObject(res.toString());
				//tell black player that they should wait for an update
				res = this.play(false); 
				waitingPlayerOut.writeObject(res.toString());
				
				
				
				
				System.out.println("Get move");
				
				
				while(!hasMoved) {
					req = parseRequest(currentPlayerIn);
					//check if request is in proper format
					if(req.get("type").equals(ChessConstants.MOVE)) {
						
						Pair from;
						Pair to; 
						try {
							from = ChessLogic.getCoordinate(req.getString("from")); 
							to = ChessLogic.getCoordinate(req.getString("to")); 
							ChessPiece selectedPiece = logic.peek(board, from); ; 
							logic.selectedCorrectColor(currentPlayer,selectedPiece); 
							//logic.inCheck(board, selectedPiece,logic.getOpponentPieces(currentPlayer), logic.getMyPieces(currentPlayer)); 
							
						
							
							validMove = logic.verifyMove(board, selectedPiece, to);
							
							if(validMove) {
								logic.moveAndUpdate(board, selectedPiece, to);
								hasMoved = true; 
								res = validMove(); 
								currentPlayerOut.writeObject(res.toString());
								res = updateBoard(from, to); 
								currentPlayerOut.writeObject(res.toString());
								waitingPlayerOut.writeObject(res.toString()); 
								 
							}
							else {
								throw new IllegalArgumentException("Invalid Move"); 
							}
							checkmate = logic.isCheckmate(board,currentPlayer); 
							
							//toggle the current color
							if(!checkmate && hasMoved) {
								
								currentPlayer = currentPlayer == ChessConstants.WHITE ? ChessConstants.BLACK: ChessConstants.WHITE; 
								res = this.continuePlaying(); 
							}
							else {
								res = this.gameOver(currentPlayer); 
							}
							p1Out.writeObject(res.toString()); 
							p2Out.writeObject(res.toString()); 
							count++; 
							
							
						}
						catch(IllegalArgumentException e) {
							//when the player tries to make an invalid move while in check
							System.out.println("Illegal Move: Player must move king while in check"); 
							e.printStackTrace();
							//write response to current player
							res = invalidMove(e.getMessage());
							currentPlayerOut.writeObject(res.toString());
							//System.exit(1);
							
						}
						catch(NullPointerException e) {
							e.printStackTrace();
							System.out.println("Illegal Move: Cannot select an empty space"); 
							res = invalidMove(e.getMessage());
							currentPlayerOut.writeObject(res.toString());
							//System.exit(1);
							
						}
						catch(Exception e) {
							//System.out.println("Error: Incorrect coordinate format for MOVE"); 
							//log error\
							e.printStackTrace();
							res = invalidMove(e.getMessage());
							currentPlayerOut.writeObject(res.toString());
							//System.exit(1);
							
						}
						
						
					}
					
					
				} //end of hasMoved block
				
			}
			
			//game is over
			inSession = false; 
			
			
		}
		catch(SocketTimeoutException e) {}
		catch(IOException e) {
			
		}finally {
			try {
				if(p1In != null) p1In.close();
				if(p1Out != null) p1Out.close();
				if(p2In != null) p2In.close();
				if(p2Out != null) p2Out.close();
				
				
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	

}
