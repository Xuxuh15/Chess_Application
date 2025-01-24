package logic;

import helpers.Pair;

public class VerifyMoveQueen implements VerificationStrategy {
	
	VerifyMoveRook rookVerification = new VerifyMoveRook(); 
	VerifyMoveBishop bishopVerification = new VerifyMoveBishop(); 
	

	@Override
	/**
	 * Verifies that move made with queen is legal
	 * @param board the chess board
	 * @param queen the queen chess piece
	 * @param newPos the destination position
	 * @return boolean indicating whether the move is legal or not
	 * @throws ArrayIndexOutOfBoundsException a move that is out of bounds is automatically illegal
	 * @throws IllegalArgumentException a move that is not legal
	 */
	public boolean verifyMove(ChessBoard board, ChessPiece queen, Pair newPos)
			throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
		
		return rookVerification.verifyMove(board, queen, newPos) || bishopVerification.verifyMove(board, queen, newPos); 
		
	}
	
	

}
