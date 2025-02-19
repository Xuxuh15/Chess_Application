package helpers;

import logic.ChessBoard;
import logic.ChessLogic;
import logic.ChessPiece;
/**
 * Class to help emulate and apply sequence of chess moves made during a match for testing purposes
 */
public class ChessEmulator {
	
	/**
	 * Chess logic
	 */
	private ChessLogic logic; 
	
	/**
	 * Constructor.
	 * @param logic chess logic
	 */
	public ChessEmulator(ChessLogic logic) {
		this.logic = logic; 
	}
	/**
	 * Takes an array representing a sequence of moves and applies sequence on chess board.
	 * @param sequence array representing a sequence of moves 
	 * @param board the chess board 
	 * @throws ArrayIndexOutOfBoundsException if a move is out of bounds
	 */
	public void applySequence(String[] sequence, ChessBoard board) throws ArrayIndexOutOfBoundsException {
		
		Pair[] arr = transformArray(sequence); 
		
		
		for(int i = 0; i < arr.length; i+=2) {
			Pair initPos = arr[i]; 
			Pair destination = arr[i+1]; 
			ChessPiece pieceToMove = logic.peek(board, initPos);
			logic.moveAndUpdate(board,pieceToMove, destination);
		}
	}
	
	/**
	 * Takes in a array representing sequence of moves to be made e.g. [1A:1C, ....] and converts it into an array of Pair objects.
	 * @param movesToApply array with string representation of moves in form initialPostion:destination 
	 * @return transformed array
	 */
	private Pair[] transformArray(String[] movesToApply) {
		
		Pair[] movesToApplyTransformed = new Pair[movesToApply.length * 2]; //since each pair is a starting position followed by destination, we need an array double original size
		int index = 0; 
		
		for(int i = 0; i < movesToApply.length; i++) {
			
			String[] moves = movesToApply[i].split(":");
			Pair initPos = extractPos(moves[0]); 
			Pair destination = extractPos(moves[1]); 
			movesToApplyTransformed[index++] = initPos; 
			movesToApplyTransformed[index++] = destination;
			
		}
		
		return movesToApplyTransformed; 
		
	}
	
	/**
	 * Takes a string coordinate and transforms it into a Pair object.
	 * @param initPos a String representation of a coordinate e.g. 1A
	 * @return a Pair object representing a coordinate on the board
	 */
	private Pair extractPos(String initPos) {
		String[] colRow = initPos.split("");  
		int row = Integer.parseInt(colRow[1]) - 1; 
		int col = logic.convertLettertoNum(colRow[0]);
		return new Pair(row, col); 
		
	}
	
	
	/**
	 * Sets the chess logic.
	 * @param logic chess logic.
	 */
	public void setLogic(ChessLogic logic) {
		this.logic = logic; 
	}

}
