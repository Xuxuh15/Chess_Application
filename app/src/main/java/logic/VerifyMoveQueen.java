package logic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import helpers.Pair;

/**
 * Verifies whether a move made by the queen is legal.
 */
public class VerifyMoveQueen implements VerificationStrategy {
	
	
	private VerifyMoveRook rookVerification = new VerifyMoveRook(); 
	private VerifyMoveBishop bishopVerification = new VerifyMoveBishop(); 
	

	@Override
	/**
	 * Verifies that move made with queen is legal
	 * @param board the chess board
	 * @param queen the queen chess piece
	 * @param newPos the destination position
	 * @return boolean indicating whether the move is legal or not
	 * @throws ArrayIndexOutOfBoundsException a move that is out of bounds is automatically illegal
	 */
	public boolean verifyMove(ChessBoard board, ChessPiece queen, Pair newPos)
			throws ArrayIndexOutOfBoundsException {
		
		//Redirect System.out to a ByteArrayOutputStream
		//this is to prevent error messaging from being logged
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);
		
		boolean validMove = rookVerification.verifyMove(board, queen, newPos) || bishopVerification.verifyMove(board, queen, newPos); 
		
		if(!validMove) {
			System.out.println("IllegalMove: Queen can move any straight direction but cannot jump pieces"); 
		}
		 System.setOut(originalOut);
		 return validMove; 
		
	}
	
	

}
