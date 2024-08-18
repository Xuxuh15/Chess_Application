package logic;
import java.lang.ArrayIndexOutOfBoundsException;

import helpers.Pair; 

public class ChessBoard implements ChessConstants {
	
	/**
	 * Double matrix to represent the game board
	 */
	private ChessPiece[][] gameBoard; 
	
	/**
	 * Constructor
	 */
	public ChessBoard() {
		this.gameBoard = new ChessPiece[ROWS][COLUMNS]; 
	}
	
	/**
	 * resets the game board
	 */
	public void resetBoard() {
		for(int i = 0; i < ROWS;i++) {
			for(int j = 0; j < COLUMNS; j++) {
				gameBoard[i][j] = null; 
			}
		}
		
	}
	
	/**
	 * Returns the chess piece at the specified location of the board
	 * @param row the row
	 * @param col the column
	 * @return the chess piece at that location or null if the space is empty
	 * @throws ArrayIndexOutOfBoundsException if the specified row or column is out of bounds
	 */
	public ChessPiece checkSpace(int row, int col) throws ArrayIndexOutOfBoundsException {
		return gameBoard[row][col]; 
		
	}
	
	/**
	 * Moves a piece on the game board
	 * @param oldPos the position of the selected chess piece
	 * @param newPos the new position
	 * @throws ArrayIndexOutOfBoundsException if one of the specified positions is out of bounds
	 */
	public void move(Pair oldPos, Pair newPos) throws ArrayIndexOutOfBoundsException {
		
		//in the case of a capture sequence, set the opposing pieces captured attribute to true
		if(gameBoard[newPos.row()][newPos.col()] != null) {
			gameBoard[newPos.row()][newPos.col()].setIsCaptured(true); 
		}
		
		//move the selected piece to its new position
		gameBoard[newPos.row()][newPos.col()] = gameBoard[oldPos.row()][oldPos.col()]; 
		//set the old position to null
		gameBoard[oldPos.row()][oldPos.col()] = null;
		
	}
	
	
	/**
	 * Gets the matrix 
	 * @return the double array storing the chess pieces
	 */
	public ChessPiece[][] getBoard(){
		return this.gameBoard; 
	}

}
