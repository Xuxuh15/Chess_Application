package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessLogic;

public class GameSession implements Runnable {
	
	private Socket p1; 
	private Socket p2; 
	private ChessLogic logic; 
	private ChessBoard board; 
	
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
		this.logic.setBlackPlayerChessArray(logic.generatePieces(ChessConstants.BLACK));
		this.logic.setWhitePlayerChessArray(logic.generatePieces(ChessConstants.WHITE));
		logic.setUpBoard(board);
		
	}
	
	
	
	
	@Override 
	public void run() {
		
		InputStream p1In; 
		InputStream p2In; 
		OutputStream p1Out; 
		OutputStream p2Out; 
		
		try {
			p1In = this.p1.getInputStream(); 
			p2In = this.p2.getInputStream(); 
			p1Out = this.p1.getOutputStream(); 
			p2Out = this.p2.getOutputStream(); 
			
			
			
		}
		catch(IOException e) {
			
		}
	}
	

}
