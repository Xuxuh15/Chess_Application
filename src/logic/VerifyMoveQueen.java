package logic;

import helpers.Pair;

/**
 * Verifies whether a move made by the queen is legal.
 */
public class VerifyMoveQueen implements VerificationStrategy {
	
	
	private VerifyMoveRook rookVerification = new VerifyMoveRook(); 
	private VerifyMoveBishop bishopVerification = new VerifyMoveBishop(); 
	

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
