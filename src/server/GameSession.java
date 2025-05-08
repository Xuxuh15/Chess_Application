package server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import helpers.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
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
	
	
	private boolean checkmate = false; 
	private char currentPlayer = ChessConstants.WHITE; 
	private boolean hasMoved = false; 
	
	public GameSession(Socket p1, Socket p2) {
		this.p1 = p1;
		this.p2 = p2; 
		this.logic = new ChessLogic(); 
		this.board = new ChessBoard(); 
	}
	
	
	public void setUpGame() {
		piecesBlack = logic.generatePieces(ChessConstants.BLACK); 
		piecesWhite = logic.generatePieces(ChessConstants.WHITE); 
		this.logic.setBlackPlayerChessArray(piecesBlack);
		this.logic.setWhitePlayerChessArray(piecesWhite);
		logic.setUpBoard(board);
		
	}
	
	public JSONObject assignColor(char color) {
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.ASSIGNMENT); 
		res.put("color", ChessConstants.WHITE); 
		return res; 
	}
	
	public JSONObject start() {
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.START); 
		return res; 
	}
	
	public JSONObject play() {
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.PLAY); 
		return res; 
	}
	
	private JSONObject validMove() {
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.VALID_MOVE); 
		res.put("ok", true); 
		return res; 
	}
	
	private JSONObject updateBoard(Pair from, Pair to) {
		
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.UPDATE); 
		String fromStr = from.row() + ":" + from.col(); 
		res.put("from", fromStr); 
		String toStr = to.row() + ":" + to.col(); 
		res.put("to", toStr); 
		
		return res;
		
	}
	private JSONObject gameOver(char color) {
		
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.END); 
		res.put("winner", color); 
		return res; 
		
	}
	
	private JSONObject continuePlaying() {
		
		JSONObject res = new JSONObject(); 
		res.put("type", ChessConstants.CONTINUE); 
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
		res.put("ok", false); 
		res.put("message", message); 
		return res; 
	}
	
	
	
	
	
	
	public Pair getCoordinate(String str)  {

		try {
			int row; 
			int col; 
			//split strings into components
			row = Integer.parseInt(str.split(":")[1]) - 1; 
			col = ChessLogic.convertLettertoNum(str.split(":")[0]);
			//return a pair object with the destination square
			return new Pair(row,col); 
		}
		catch(InputMismatchException e) { //wrong input format
			throw e; 
		}
		catch(NumberFormatException e) {
			throw e; 
		}
	}
	
	
	private boolean selectedCorrectColor(char currentPlayer, ChessPiece selectedPiece) throws IllegalArgumentException {
		if(selectedPiece.getColor() != currentPlayer) {
			return true; 
		} 
		throw new IllegalArgumentException("Illegal Move: Player cannot select piece of opponent's color"); 
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
			
			//indicate that the game has started
			res = this.start();  
			
			
			while(inSession && !checkmate) {
				
				ObjectInputStream currentPlayerIn; 
				ObjectOutputStream currentPlayerOut;  
				ObjectOutputStream waitingPlayerOut; 
				
				res = this.play(); 
				
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
				
				currentPlayerOut.writeObject(res);
				req = parseRequest(currentPlayerIn); 
				
				
				while(!hasMoved) {
					
					//check if request is in proper format
					if(req.get("type").equals(ChessConstants.MOVE)) {
						
						Pair from;
						Pair to; 
						try {
							from = getCoordinate(req.getString("from")); 
							to = getCoordinate(req.getString("to")); 
							ChessPiece selectedPiece = logic.peek(board, from); 
							this.selectedCorrectColor(currentPlayer,selectedPiece); 
							logic.inCheck(board, selectedPiece,logic.getOpponentPieces(currentPlayer), logic.getMyPieces(currentPlayer)); 
							
							validMove = logic.verifyMove(board, selectedPiece, to); 
							
							if(validMove) {
								logic.moveAndUpdate(board, selectedPiece, to);
								hasMoved = true; 
								res = validMove(); 
								currentPlayerOut.writeObject(res);
								res = updateBoard(from, to); 
								currentPlayerOut.writeObject(res);
								waitingPlayerOut.writeObject(res); 
							}
							else {
								throw new IllegalArgumentException("Invalid Move"); 
							}
							this.checkmate = logic.isCheckmate(board,currentPlayer); 
							
							//toggle the current color
							if(!checkmate && hasMoved) {
								currentPlayer = currentPlayer == ChessConstants.WHITE ? ChessConstants.BLACK: ChessConstants.WHITE; 
								hasMoved = false; 
								res = this.continuePlaying(); 
							}
							else {
								res = this.gameOver(currentPlayer); 
							}
							p1Out.writeObject(res); 
							p2Out.writeObject(res); 
							
							
						}
						catch(IllegalArgumentException e) {
							//when the player tries to make an invalid move while in check
							System.out.println("Illegal Move: Player must move king while in check"); 
							//write response to current player
							res = invalidMove(e.getMessage());
							currentPlayerOut.writeObject(res);
							
						}
						catch(NullPointerException e) {
							System.out.println("Illegal Move: Cannot select an empty space"); 
							res = invalidMove(e.getMessage());
							currentPlayerOut.writeObject(res);
							
						}
						catch(Exception e) {
							System.out.println("Error: Incorrect coordinate format for MOVE"); 
							//log error
							
						}
						
						
					}
					
					
				} //end of hasMoved block
				
			}
			
			//game is over
			inSession = false; 
			
			
		}
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
