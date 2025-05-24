package logic;


import helpers.Pair;

/**
 * Verifies whether a move made with a knight is legal.
 */
public class VerifyMoveKnight implements VerificationStrategy{
	
	/**
	 * Chess logic.
	 */
    private ChessLogic logic = new ChessLogic(); 

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
    	
    	boolean validMove = false; 
    	try {
			int deltaY = Math.abs(knight.getPos().row() - newPos.row()); 
			int deltaX = Math.abs(knight.getPos().col() - newPos.col());
			
			//make sure move is not over bounds
			if(deltaY > 2 || deltaX > 2 || deltaX <= 0 || deltaY <= 0) {
				throw new IllegalArgumentException("Illegal Move:  Knight must move in a L-sequence "); 
			}
			
			//make sure the move is a L-shape
			if(deltaY == 2 && deltaX != 1 || deltaY == 1 && deltaX != 2) {
				throw new IllegalArgumentException("Illegal Move: Knight must move in a L-sequence"); 
			}
			
			//if the move is valid, then the destination square must be empty or able to capture
			
			validMove = logic.isEmpty(board, newPos.row(), newPos.col()) || logic.isCapturable(board,knight,newPos); 
		}
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage()); 
		}
    	return validMove;
      
    }
    
}
