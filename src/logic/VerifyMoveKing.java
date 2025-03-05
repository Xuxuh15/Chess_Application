package logic;

import helpers.Pair;

/**
 * Verifies whether a move made with a king is legal.
 */
public class VerifyMoveKing implements VerificationStrategy {
	
	/**
	 * Chess logic.
	 */
	private ChessLogic logic = new ChessLogic(); 
	
	/**
	 * Algorithm determines if a king is in check by checking position of opponents pieces.
	 */
	private ChessPiece[] opponentPieces; 
	
	/**
	 * Constructor
	 * @param oppoentPieces array of opponents pieces.
	 */
	public VerifyMoveKing(ChessPiece[] oppPieces) {
		this.opponentPieces = oppPieces;
	}
	

	@Override
	/**
	 * Verifies that move made with a king is legal
	 * @param board the chess board
	 * @param arr array of opponents chess pieces
	 * @param king the king piece
	 * @param newPos the destination position
	 * @return boolean value that indicates whether the move was legal
	 * @throws ArrayIndexOutOfBoundsException if the destination square is out of bounds
	 */
	public boolean verifyMove(ChessBoard board, ChessPiece king, Pair newPos)
			throws ArrayIndexOutOfBoundsException{
		
		boolean validMove = false; 
		try {
			//change in x and y coordinates 
			int deltaX = newPos.col() - king.getPos().col(); 
			int deltaY = newPos.row() - king.getPos().row(); 
			
			if(deltaY > 1 || deltaX > 1) {
				throw new IllegalArgumentException("Illegal Move: King can only move one space at a time"); 
			}
			
			if(logic.isEmpty(board, newPos.row(), newPos.col()) || logic.isCapturable(board, king, newPos) ) {
				if(logic.isChecked(board,this.opponentPieces, king.getPos())) {
					throw new IllegalArgumentException("Illegal Move: King cannot move into check");
				}
			}
			validMove = true; 
		}
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage()); 
		}

		return validMove; 
	}

}
