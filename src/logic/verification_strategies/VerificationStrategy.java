package logic.verification_strategies;

import logic.ChessBoard;
import logic.ChessPiece;
import helpers.Pair;

public interface VerificationStrategy {


    public boolean verifyMove(ChessBoard board, ChessPiece pieceToMove, Pair destinationSquare) 
    throws ArrayIndexOutOfBoundsException; 
    
}
