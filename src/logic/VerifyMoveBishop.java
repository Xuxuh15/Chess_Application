package logic;

import helpers.Pair;
import logic.ChessBoard;
import logic.ChessPiece;
import logic.ChessLogic;

public class VerifyMoveBishop implements VerificationStrategy {

    ChessLogic logic = new ChessLogic(); 

    @Override
    /**
		 * Verifies that move with bishop is legal
		 * @param board the chess board
		 * @param bishop the chess piece being moved
		 * @param newPos the destination position
		 * @return boolean indicates whether move made is legal
		 * @throws ArrayIndexOutOfBoundsException a move that is out of bounds is automatically illegal
		 * @throws IllegalArgumentException a move that is not legal
		 */
    public boolean verifyMove(ChessBoard board, ChessPiece bishop, Pair newPos) throws ArrayIndexOutOfBoundsException, IllegalArgumentException{

        int deltaX = newPos.col() - bishop.getPos().col(); 
			int deltaY = newPos.row() - bishop.getPos().row(); 
			
			
			//make sure the piece moves along a diagonal axis
			if(Math.abs(deltaX) != Math.abs(deltaY)) {
				throw new IllegalArgumentException("Illegal Move:" + bishop.toString() + " can only move along the diaginal axis."); 
			}
			
			//make sure the new position is not equal to the old position
			if(deltaX == 0 && deltaY == 0) {
				throw new IllegalArgumentException("Illegal Move:" + bishop.toString() + " is already in that square.");  
			}
			
			//gives us the direction the bishop is moving along x and y axis
			 int uVectorX = deltaX > 0 ? 1 : -1;
			 int uVectorY = deltaY > 0 ? 1 : -1;
			
			int x_coord = bishop.getPos().col() + uVectorX; 
			int y_coord = bishop.getPos().row() + uVectorY; 
			
			while(y_coord != newPos.row() && x_coord != newPos.col()) {
				//make sure that the path to the destination is free
				if(!logic.isEmpty(board, y_coord, x_coord)) {
					throw new IllegalArgumentException("Illegal Move:" + bishop.toString() + " cannot jump pieces"); 
				}
				x_coord += uVectorX; 
				y_coord += uVectorY; 
			}
			
			//check the destination square. Make sure if it is either empty or can be captured
			return logic.isEmpty(board, newPos.row(), newPos.col()) || logic.isCapturable(board, bishop, newPos); 
    }



    
}
