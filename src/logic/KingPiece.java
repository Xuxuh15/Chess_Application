package logic;

import helpers.Pair;

/**
 * A class representing a King chess piece
 */
public class KingPiece extends ChessPiece implements ChessConstants {
	 
	/**
	 * Whether the king is in check
	 */
	private boolean isChecked; 
	
	/**
	 * Constructor
	 * @param color the color of the piece
	 */
	public KingPiece(char color) {
		super(KING,color); 
		this.isChecked = false;
		//set the starting position of the king
		if(color == WHITE) {
			this.setPos(0,E); 
		}
		else {
			this.setPos(7,E); 
		}
		
	}
	
	/**
	 * Checks if the king is currently checked
	 * @return boolean isChecked whether the king is in check
	 */
	public boolean isChecked() {
		return this.isChecked; 
	}
	
	/**
	 * Sets the King's isChecked attribute
	 * @param val boolean value
	 */
	public void setIsChecked(boolean val) {
		this.isChecked = val; 
	}
	
	
	
}
