package logic;


import helpers.Pair;
import logic.ChessBoard;
import logic.ChessLogic;
import logic.ChessPiece;


public class VerifyMoveKnight implements VerificationStrategy{

    ChessLogic logic = new ChessLogic(); 

    @Override
    /**
		 * Verifies move with knight is legal
		 * @param board the chess board
		 * @param knight the chess piece to be moved
		 * @param newPos the new position
		 * @return boolean indicates whether move is legal or not
		 * @throws ArrayIndexOutOfBoundsException a move that is out of bounds is automatically illegal
		 */
    public boolean verifyMove(ChessBoard board, ChessPiece knight, Pair newPos) throws ArrayIndexOutOfBoundsException {

				int deltaY = Math.abs(knight.getPos().row() - newPos.row()); 
				int deltaX = Math.abs(knight.getPos().col() - newPos.col());
				
				//make sure move is not over bounds
				if(deltaY > 2 || deltaX > 2 || deltaX <= 0 || deltaY <= 0) {
					System.out.println("Illegal Move:  Knight must move in a L-sequence "); 
					return false; 
				}
				
				//make sure the move is a L-shape
				if(deltaY == 2 && deltaX != 1 || deltaY == 1 && deltaX != 2) {
					System.out.print("Illegal Move: Knight must move in a L-sequence");
					return false; 
				}
				
				//if the move is valid, then the destination square must be empty or able to capture
				
				return logic.isEmpty(board, newPos.row(), newPos.col()) || logic.isCapturable(board,knight,newPos); 
			
      
    }
    
}
