package logic;

import helpers.Pair;

/**
 * Class that represents a chess piece
 * @author xuxuh
 */
public class ChessPiece implements ChessConstants {

	/**
	 * chess piece traditional value
	 */
	private int value; 
	/**
	 * the rank of the chess piece
	 */
	private int rank; 
	/**
	 * color of the chess piece: white or black
	 */
	private char color; 
	/**
	 * boolean to represent whether the chess piece has been captured
	 */
	private boolean isCaptured; 
	
	/**
	 * boolean represents whether the chess piece has been moved
	 */
	private boolean hasMoved; 
	
	/**
	 * The coordinate of the piece on the chess board
	 */
	private Pair coord; 
	
	/**
	 * Constructor
	 * @param rank the rank of the chess piece
	 * @param color the color of the piece [black,white]
	 */
	public ChessPiece(int rank, char color) {

		this.rank = rank; 
		this.color = color; 
		this.isCaptured = false;
		this.hasMoved = false; 
		this.coord = new Pair(0,0); 
		
		switch(rank) {
		
		case PAWN:
			this.value = 1; 
			break; 
		case KNIGHT:
			this.value = 3; 
			break; 
		case BISHOP:
			this.value = 3;
			break; 
		case ROOK:
			this.value = 5;
			break; 
		case QUEEN:
			this.value = 9; 
			break; 
		default:
			this.value = 0; 
			break; 
		}
	}
	
	/**
	 * Default constructor
	 */
	public ChessPiece() {
		this.value = 0; 
		this.rank = PAWN; 
		this.color = ' '; 
		this.isCaptured = false;
		this.coord = new Pair(0,0); 
		
	}
	
	
	/**
	 * Returns the chess piece value
	 * @return int value the value of the chess piece
	 */
	public int getValue() {
		return this.value; 
	}
	
	/**
	 * Returns the rank of the chess piece
	 * @return int rank 
	 */
	public int getRank() {
		return this.rank; 
	}
	
	/**
	 * Returns the color of the chess piece
	 * @return char color
	 */
	public char getColor() {
		return this.color; 
	}
	
	/**
	 * Checks if the piece has been captured
	 * @return boolean captured whether the piece has been captured or not
	 */
	public boolean getIsCaptured() {
		return this.isCaptured; 
	}
	
	/**
	 * Toggles the chess piece's captured value
	 */
	public void toggleCaptured() {
		if(!isCaptured) {
			isCaptured = true; 
		}
		else {
			isCaptured = false;
		}
	}
	
	/**
	 * Sets isCaptured attribute to provided boolean parameter
	 * @param val boolean value
	 */
	public void setIsCaptured(boolean val) {
		this.isCaptured = val; 
	}
	
	/**
	 * Sets the rank of the chess piece
	 * @param rank the rank to be set
	 */
	public void setRank(int rank) {
		this.rank = rank; 
	}
	
	/**
	 * Sets the color of the chess piece
	 * @param color the color to be set
	 */
	public void setColor(char color) {
		this.color = color; 
	}
	
	/**
	 * Sets the value of the chess piece
	 * @param value the value to be set
	 */
	public void setValue(int value) {
		this.value = value; 
	}
	
	/**
	 * Sets hasMoved attribute to boolean value provided
	 * @param val boolean value
	 */
	public void setHasMoved(boolean val) {
		this.hasMoved = val; 
	}
	
	/**
	 * Used to set the instances hasMoved attribute to true
	 */
	public void willMove() {
		if (this.hasMoved == false) {
			this.hasMoved = true; 
		}
	}
	
	/**
	 * Checks if the king has been moved. Necessary for verifying whether
	 * a king can castle
	 * @return boolean hasMoved whether the king has moved or not
	 */
	public boolean hasMoved() {
		return this.hasMoved; 
	}
	
	/**
	 * Gets the chess piece token
	 * @return the token representation of the chess piece
	 */
	public char getToken() {
		char token = ' '; 
		switch(this.rank) {
		
		case PAWN:
			token = '\u2659'; 
			break; 
		case KNIGHT:
			token ='\u2658';
			break;
		case BISHOP:
			token ='\u2657'; 
			break;
		case ROOK:
			token = '\u2656'; 
			break;
		case QUEEN:
			token = '\u2655'; 
			break;
		case KING:
			token = '\u2654'; 
			break;
		default:
			token = ' '; 
			break;
			
		}
		
		return token; 
	}
	
	/**
	 * Returns the current position of the king
	 * @return
	 */
	public Pair getPos() {
		return this.coord; 
	}
	
	/**
	 * Sets the current position of the king
	 * @param row the row
	 * @param col the column
	 */
	public void setPos(int row, int col) {
		this.coord.setRow(row);
		this.coord.setCol(col); 
	}
	
	
	/**
	 * Returns string representation of object
	 */
	@Override 
	public String toString() {
		String str = ""; 
		switch(this.rank) {
		case PAWN:
			str = "Pawn"; 
			break; 
		case KNIGHT:
			str = "Knight";
			break; 
		case BISHOP:
			str = "Bishop"; 
			break;
		case ROOK:
			str = "Rook"; 
			break; 
		case QUEEN:
			str = "Queen"; 
			break; 
		case KING:
			str = "King"; 
			break; 
		default:
			str = "Pawn"; 
			break; 
			
		}
		return str; 
	}
	
}
