package logic;

import helpers.Pair;

public class VerifyMoveRook implements VerificationStrategy  {
	
	ChessLogic logic = new ChessLogic(); 

	@Override
	/**
	 * Verifies that the move made by a rook is legal
	 * @param board the game board
	 * @param rook a rook chess piece
	 * @param newPos the destination
	 * @throws ArrayIndexOutOfBoundsException a move that is out of bounds is automatically illegal
	 * @return boolean indicates whether the move is legal or not
	 */
	public boolean verifyMove(ChessBoard board, ChessPiece rook, Pair newPos)
			throws ArrayIndexOutOfBoundsException {
		//change in x and y coordinates 
		int deltaX = newPos.col() - rook.getPos().col(); 
		int deltaY = newPos.row() - rook.getPos().row(); 
		
		
		//make sure the rook moves either horizontally or vertically but not both
		if(!(deltaX == 0 ^ deltaY == 0) ) {
			System.out.println("Illegal Move:" + rook.toString() +  " can move either horizontally or vertically but not both");
			return false; 
		}
		
		//temporary coordinates
		int x_coord; 
		int y_coord; 
		
		 // a vertical move
		if(deltaX == 0) {
			//get the direction along the vertical axis (essentially a unit vector)
			 int vectorY = deltaY > 0 ? 1 : -1; 
			 y_coord = rook.getPos().row() + vectorY;
			 x_coord = rook.getPos().col(); 
			 
			 //traverse the path (y-axis) to the destination
			 while(y_coord != newPos.row()) {
				 if(!logic.isEmpty(board,y_coord, x_coord)) {
					 System.out.println("Illegal Move:" + rook.toString() + " cannot jump pieces"); 
					 return false; 
				 }
				 //increment counter var
				 y_coord += vectorY;
			 }
			 
		 }
		//a horizontal move
		else {
			//get the direction along the horizontal axis (essentially a unit vector)
			 int vectorX = deltaX > 0 ? 1 : -1; 
			 y_coord = rook.getPos().row();
			 x_coord = rook.getPos().col() + vectorX; 
			 //traverse path (x-axis) to the destination
			while(x_coord != newPos.col()) {
				if(!logic.isEmpty(board,y_coord, x_coord)) {
					 System.out.println("Illegal Move:" + rook.toString() + " cannot jump pieces"); 
					 return false; 
				 }
				 //increment counter var
				 x_coord += vectorX;
			}
		}
		
		//check the destination square. Make sure if it is either empty or can be captured
		return logic.isEmpty(board, newPos.row(), newPos.col()) || logic.isCapturable(board, rook, newPos); 
	}
    
}
