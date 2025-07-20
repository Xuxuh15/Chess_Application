package logic;


import helpers.Pair; 

/**
 * Verifies whether a move made with a pawn is legal. 
 */
public class VerifyMovePawn implements VerificationStrategy {
	
	/**
	 * Chess logic.
	 */
    private ChessLogic logic = new ChessLogic(); 

    @Override
    	/**
		 * Verifies move made with pawn is legal
		 * @param board the chess board
		 * @param pawn the chess piece
		 * @param newPos the destination position
		 * @return boolean indicates whether move made is legal
		 * @throws ArrayIndexOutOfBoundsException a move that is out of bounds is automatically illegal
		 * @throws IllegalArgumentException if a move is not legal
		 */
    public boolean verifyMove(ChessBoard board, ChessPiece pawn, Pair newPos) throws ArrayIndexOutOfBoundsException{
        
    	boolean validMove = false; 
    	try {
    		int deltaY = Math.abs(pawn.getPos().row() - newPos.row()); 
			int deltaX = Math.abs(pawn.getPos().col() - newPos.col());	
			int uVector; 
			
			
			uVector = pawn.getColor() == ChessConstants.WHITE ? 1: -1; 
			
			
			//check that the pawn is moving forward
			if(pawn.getColor() == ChessConstants.WHITE && pawn.getPos().row() - newPos.row() > 0) {
				throw new IllegalArgumentException("Illegal Move: Pawn must move forward"); 
			}
			else if(pawn.getColor() == ChessConstants.BLACK && pawn.getPos().row() - newPos.row() < 0) {
				throw new IllegalArgumentException("Illegal Move: Pawn must move forward"); 
			}


			//capture sequence 
			if(deltaY == 1 && deltaX == 1) {
				
				validMove = !logic.isEmpty(board, newPos.row(), newPos.col()) && logic.isCapturable(board,pawn,newPos); 
			}
			//moving two spaces forward 
			else if(deltaY == 2 && deltaX == 0) {
				//pawn can only move two spaces forward from its starting position
				if(pawn.hasMoved()) {
					throw new IllegalArgumentException("Illegal Move: Pawn can only move one space"); 
				}
				//pawn cannot move forward if opposing pieces are blocking it
				else if(!logic.isEmpty(board, pawn.getPos().row() + uVector, pawn.getPos().col()) || !logic.isEmpty(board,newPos.row(), newPos.col())) {
					throw new IllegalArgumentException("Illegal Move: Pawn cannot move over occupied square"); 
				}
				validMove = true; 
			}
			//pawn is moving forward one space
			else if(deltaY == 1 && deltaX == 0) {
				//if the space the pawn wants to move is occupied, move is illegal
				if(!logic.isEmpty(board,newPos.row(), newPos.col())) {
					throw new IllegalArgumentException("Illegal Move: Pawn cannot move into occupied square unless capturing"); 
				}
				validMove = true; 
			}
			
    	}
    	catch(IllegalArgumentException e) {
    		System.out.println(e.getMessage()); 
    	}
    	return validMove; 
    	
    }



    
}
