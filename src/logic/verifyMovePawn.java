

import helpers.Pair;
import logic.*; 

public class verifyMovePawn implements VerificationStrategy {

    ChessLogic logic = new ChessLogic(); 

    @Override
    	/**
		 * Verifies move made with pawn is legal
		 * @param board the chess board
		 * @param pawn the chess piece
		 * @param newPos the destination position
		 * @return boolean indicates whether move made is legal
		 * @throws ArrayIndexOutOfBoundsException a move that is out of bounds is automatically illegal
		 */
    public boolean verifyMove(ChessBoard board, ChessPiece pawn, Pair newPos) throws ArrayIndexOutOfBoundsException {
        int deltaY = Math.abs(pawn.getPos().row() - newPos.row()); 
			int deltaX = Math.abs(pawn.getPos().col() - newPos.col());	
			int uVector; 
			
			
			uVector = pawn.getColor() == WHITE ? 1: -1; 
			
			
			
			//check that the pawn is moving forward
			if(pawn.getColor() == ChessConstants.WHITE && pawn.getPos().row() - newPos.row() > 0) {
				System.out.println("Illegal Move: Pawn must move forward"); 
				return false;
			}
			else if(pawn.getColor() == ChessConstants.BLACK && pawn.getPos().row() - newPos.row() < 0) {
				System.out.println("Illegal Move: Pawn must move forward"); 
				return false;
			}


			//capture sequence 
			if(deltaY == 1 && deltaX == 1) {
				
				return !logic.isEmpty(board, newPos.row(), newPos.col()) && logic.isCapturable(board,pawn,newPos); 
			}
			//moving two spaces forward 
			else if(deltaY == 2 && deltaX == 0) {
				//pawn can only move two spaces forward from its starting position
				if(pawn.hasMoved()) {
					System.out.println("Illegal Move: Pawn can only move one space"); 
					return false; 
				}
				//pawn cannot move forward if opposing pieces are blocking it
				else if(!logic.isEmpty(board, pawn.getPos().row() + uVector, pawn.getPos().col()) || !logic.isEmpty(board,newPos.row(), newPos.col())) {
					System.out.println("Illegal Move: Pawn cannot move over occupied square"); 
					return false;
				}
				return true; 
			}
			//pawn is moving forward one space
			else if(deltaY == 1 && deltaX == 0) {
				//if the space the pawn wants to move is occupied, move is illegal
				if(!logic.isEmpty(board,newPos.row(), newPos.col())) {
					System.out.println("Illegal Move: Pawn cannot move into occupied square unless capturing"); 
					return false; 
				}
				return true; 
			}
			
			//player inputed a illegal move
			return false;
    }



    
}
