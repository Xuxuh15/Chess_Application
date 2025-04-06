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
		res.put("type", "move"); 
		res.put("ok", true); 
		return res; 
	}
	
	public JSONObject parseRequest(ObjectInputStream in) {
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
	
	/**
	 * Throws an exception if player tries to move a chess piece that is not the king while in check
	 * @param currentPlayer the current player color
	 * @param selectedPiece the currently selected piece
	 * @return false if not in check
	 * @throws IllegalArgumentException if the selected piece is not the king
	 */
	public boolean inCheck(char currentPlayer, ChessPiece selectedPiece) throws IllegalArgumentException {
		
		if(currentPlayer == ChessConstants.WHITE) {
			if(this.logic.isChecked(this.board, this.piecesBlack, this.piecesWhite[ChessConstants.INDEXKING].getPos())){
				if(selectedPiece.getRank() != ChessConstants.KING) {
					throw new IllegalArgumentException("Illegal Selection: King is in check"); 
					 
				}
			}
		}
		else {
			if(logic.isChecked(this.board, this.piecesWhite, this.piecesBlack[ChessConstants.INDEXKING].getPos())){
				if(selectedPiece.getRank() != ChessConstants.KING) {
					throw new IllegalArgumentException("Illegal Selection: King is in check"); 
				}
			}
		}
		
		return false; 
		
		
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
		
		ObjectInputStream p1In; 
		ObjectInputStream p2In; 
		ObjectOutputStream p1Out; 
		ObjectOutputStream p2Out; 
		
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
				
				
				while(!hasMoved) {
					//white's turn to move
					res = this.play(); 
					p1Out.writeObject(res);
					req = parseRequest(p1In); 
					if(req.get("ok").equals(true)) {
						
						Pair from;
						Pair to; 
						try {
							from = getCoordinate(req.getString("from")); 
							to = getCoordinate(req.getString("to")); 
							ChessPiece selectedPiece = logic.peek(board, from); 
							this.selectedCorrectColor(currentPlayer,selectedPiece); 
							this.inCheck(currentPlayer, selectedPiece); 
							
							validMove = logic.verifyMove(board, selectedPiece, to); 
							
							if(validMove) {
								logic.moveAndUpdate(board, selectedPiece, to);
								hasMoved = true; 
								res = validMove(); 
								p1Out.writeObject(res);
							}
							else {
								throw new IllegalArgumentException("Invalid Move"); 
							}
							
							
						}
						catch(IllegalArgumentException e) {
							//when the player tries to make an invalid move while in check
							System.out.println("Illegal Move: Player must move king while in check"); 
							//write response to current player
						}
						catch(Exception e) {
							System.out.println("Error: Incorrect coordinate format for MOVE"); 
							//log error
							
						}
						
							
						
					}
					
				}
				
				
				
				
				
				
			}
			
			
			
			
			
		}
		catch(IOException e) {
			
		}
	}
	

}
