package logic;

import helpers.Pair;

/**
 * Interface that defines strategy classes to verify legal moves in a game of chess.
 */
public interface VerificationStrategy {

	/**
	 * Verifies whether a move made is legal or not.
	 * @param board the chess board.
	 * @param pieceToMove the chess piece to move
	 * @param destinationSquare the destination square
	 * @return boolean whether the move is legal or not
	 * @throws ArrayIndexOutOfBoundsException if the destination square is out of bounds
	 * @throws IllegalArgumentException if the move does not match the chess piece's move pattern
	 */
    boolean verifyMove(ChessBoard board, ChessPiece pieceToMove, Pair destinationSquare) 
    throws ArrayIndexOutOfBoundsException, IllegalArgumentException; 
    
}
