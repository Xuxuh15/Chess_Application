package logic;

import helpers.Pair;

public interface VerificationStrategy {


    boolean verifyMove(ChessBoard board, ChessPiece pieceToMove, Pair destinationSquare) 
    throws ArrayIndexOutOfBoundsException, IllegalArgumentException; 
    
}
