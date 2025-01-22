package logic;

import helpers.Pair;

public class verifyMoveQueen implements VerificationStrategy {
	
	verifyMoveRook rookVerification = new verifyMoveRook(); 
	verifyMoveBishop bishopVerification = new verifyMoveBishop(); 
	

	@Override
	/**
	 * Verifies that move made with queen is legal
	 * @param board the chess board
	 * @param queen the queen chess piece
	 * @param newPos the destination position
	 * @return boolean indicating whether the move is legal or not
	 */
	public boolean verifyMove(ChessBoard board, ChessPiece queen, Pair newPos)
			throws ArrayIndexOutOfBoundsException {
		
		return rookVerification.verifyMove(board, queen, newPos) || bishopVerification.verifyMove(board, queen, newPos); 
		
	}
	
	

}
