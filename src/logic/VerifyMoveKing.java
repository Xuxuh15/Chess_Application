package logic;

import helpers.Pair;

public class VerifyMoveKing implements VerificationStrategy {
	
	private ChessLogic logic = new ChessLogic(); 
	
	private ChessPiece[] opponentPieces; 
	
	public VerifyMoveKing(ChessPiece[] oppoentPieces) {
		this.opponentPieces = opponentPieces;
		
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
	 * @throws IllegalArgumentException if the move is not legal
	 */
	public boolean verifyMove(ChessBoard board, ChessPiece king, Pair newPos)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
		
		//change in x and y coordinates 
		int deltaX = newPos.col() - king.getPos().col(); 
		int deltaY = newPos.row() - king.getPos().row(); 
		
		if(deltaY > 1 || deltaX > 1) {
			throw new IllegalArgumentException("Illegal Move: King can only move one space at a time"); 
		}
		
		if(logic.isEmpty(board, newPos.row(), newPos.col()) || logic.isCapturable(board, king, newPos) ) {
			if(logic.isChecked(board,this.opponentPieces, king.getPos())) {
				throw new IllegalArgumentException("Illegal Move: King can only move one space at a time");
			}
		}
		
		return true; 
	}

}
