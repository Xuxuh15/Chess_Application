package logic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import helpers.Pair;

/**
 * Class that is responsible for handling the chess game logic
 * @author xuxuh
 */
public class ChessLogic implements ChessConstants {
	

	//must add  en pesant rule later
		/**
		 * Verifies move made with pawn is legal
		 * @param board the chess board
		 * @param pawn the chess piece
		 * @param newPos the destination position
		 * @return boolean indicates whether move made is legal
		 * @throws ArrayIndexOutOfBoundsException a move that is out of bounds is automatically illegal
		 */
		public boolean verifyMovePawn(ChessBoard board, ChessPiece pawn, Pair newPos) 
			throws ArrayIndexOutOfBoundsException{
			
			int deltaY = Math.abs(pawn.getPos().row() - newPos.row()); 
			int deltaX = Math.abs(pawn.getPos().col() - newPos.col());	
			int uVector; 
			
			
			uVector = pawn.getColor() == WHITE ? 1: -1; 
			
			
			
			//check that the pawn is moving forward
			if(pawn.getColor() == WHITE && pawn.getPos().row() - newPos.row() > 0) {
				System.out.println("Illegal Move: Pawn must move forward"); 
				return false;
			}
			else if(pawn.getColor() == BLACK && pawn.getPos().row() - newPos.row() < 0) {
				System.out.println("Illegal Move: Pawn must move forward"); 
				return false;
			}


			//capture sequence 
			if(deltaY == 1 && deltaX == 1) {
				
				return !isEmpty(board, newPos.row(), newPos.col()) && isCapturable(board,pawn,newPos); 
			}
			//moving two spaces forward 
			else if(deltaY == 2 && deltaX == 0) {
				//pawn can only move two spaces forward from its starting position
				if(pawn.hasMoved()) {
					System.out.println("Illegal Move: Pawn can only move one space"); 
					return false; 
				}
				//pawn cannot move forward if opposing pieces are blocking it
				else if(!isEmpty(board, pawn.getPos().row() + uVector, pawn.getPos().col()) || !isEmpty(board,newPos.row(), newPos.col())) {
					System.out.println("Illegal Move: Pawn cannot move over occupied square"); 
					return false;
				}
				return true; 
			}
			//pawn is moving forward one space
			else if(deltaY == 1 && deltaX == 0) {
				//if the space the pawn wants to move is occupied, move is illegal
				if(!isEmpty(board,newPos.row(), newPos.col())) {
					System.out.println("Illegal Move: Pawn cannot move into occupied square unless capturing"); 
					return false; 
				}
				return true; 
			}
			
			//player inputed a illegal move
			return false;
		}
		
		/**
		 * A function that checks if the target square is empty
		 * @param board the chess board
		 * @param row the target row
		 * @param col the target column
		 * @return boolean indicates if target square is empty or not
		 */
		public boolean isEmpty(ChessBoard board, int row, int col) {
			return board.checkSpace(row, col) == null; 
		}
		
		/**
		 * Function that indicates whether piece in target square can be captured
		 * @param board the chess board
		 * @param p the chess piece attempting a capture
		 * @param dest the target square
		 * @return boolean indicates whether target square can be captured
		 * @throws ArrayIndexOutOfBoundsException a square out of bounds cannot be captured
		 */
		public boolean isCapturable(ChessBoard board, ChessPiece p, Pair dest) 
			throws ArrayIndexOutOfBoundsException{
			if(board.checkSpace(dest.row(), dest.col()).getColor() == p.getColor()) {
				return false;
			}
			return true; 
		}
		
		
		
		/**
		 * Verifies move with knight is legal
		 * @param board the chess board
		 * @param knight the chess piece to be moved
		 * @param newPos the new position
		 * @return boolean indicates whether move is legal or not
		 * @throws ArrayIndexOutOfBoundsException a move that is out of bounds is automatically illegal
		 */
		public boolean verifyMoveKnight(ChessBoard board,ChessPiece knight, Pair newPos) 
				throws ArrayIndexOutOfBoundsException{
				
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
				
				return isEmpty(board, newPos.row(), newPos.col()) || isCapturable(board,knight, newPos); 
			
			}
		
		/**
		 * Verifies that move with bishop is legal
		 * @param board the chess board
		 * @param bishop the chess piece being moved
		 * @param newPos the destination position
		 * @return boolean indicates whether move made is legal
		 * @throws ArrayIndexOutOfBoundsException a move that is out of bounds is automatically illegal
		 */
		public boolean verifyMoveBishop(ChessBoard board, ChessPiece bishop, Pair newPos) 
			throws ArrayIndexOutOfBoundsException{
			
			int deltaX = newPos.col() - bishop.getPos().col(); 
			int deltaY = newPos.row() - bishop.getPos().row(); 
			
			
			//make sure the piece moves along a diagonal axis
			if(Math.abs(deltaX) != Math.abs(deltaY)) {
				System.out.println("Illegal Move:" + bishop.toString() + " can only move along the diaginal axis."); 
				return false; 
			}
			
			//make sure the new position is not equal to the old position
			if(deltaX == 0 && deltaY == 0) {
				System.out.println("Illegal Move:" + bishop.toString() + " is already in that square."); 
				return false; 
			}
			
			//gives us the direction the bishop is moving along x and y axis
			 int uVectorX = deltaX > 0 ? 1 : -1;
			 int uVectorY = deltaY > 0 ? 1 : -1;
			
			int x_coord = bishop.getPos().col() + uVectorX; 
			int y_coord = bishop.getPos().row() + uVectorY; 
			
			while(y_coord != newPos.row() && x_coord != newPos.col()) {
				//make sure that the path to the destination is free
				if(!isEmpty(board, y_coord, x_coord)) {
					System.out.println("Illegal Move:" + bishop.toString() + " cannot jump pieces"); 
					return false; 
				}
				x_coord += uVectorX; 
				y_coord += uVectorY; 
			}
			
			//check the destination square. Make sure if it is either empty or can be captured
			return isEmpty(board, newPos.row(), newPos.col()) || isCapturable(board, bishop, newPos); 
			
			
		}
		
		/**
		 * Verifies that the move made by a rook is legal
		 * @param board the game board
		 * @param rook a rook chess piece
		 * @param newPos the destination
		 * @throws ArrayIndexOutOfBoundsException a move that is out of bounds is automatically illegal
		 * @return boolean indicates whether the move is legal or not
		 */
		public boolean verifyMoveRook(ChessBoard board, ChessPiece rook, Pair newPos) 
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
					 if(!isEmpty(board,y_coord, x_coord)) {
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
					if(!isEmpty(board,y_coord, x_coord)) {
						 System.out.println("Illegal Move:" + rook.toString() + " cannot jump pieces"); 
						 return false; 
					 }
					 //increment counter var
					 x_coord += vectorX;
				}
			}
			
			//check the destination square. Make sure if it is either empty or can be captured
			return isEmpty(board, newPos.row(), newPos.col()) || isCapturable(board, rook, newPos); 
			
		}
		
		/**
		 * Verifies that move made with queen is legal
		 * @param board the chess board
		 * @param queen the queen chess piece
		 * @param newPos the destination position
		 * @return boolean indicating whether the move is legal or not
		 */
		public boolean verifyMoveQueen(ChessBoard board, ChessPiece queen, Pair newPos) {
			
			return verifyMoveRook(board, queen, newPos) || verifyMoveRook(board, queen, newPos); 
		}
		
		
		
		
	
		
		/**
		 * Verifies that move made with a king is legal
		 * @param board the chess board
		 * @param arr array of opponents chess pieces
		 * @param king the king piece
		 * @param newPos the destination position
		 * @return boolean value that indicates whether the move was legal
		 * @throws ArrayIndexOutOfBoundsException if the destination square is out of bounds
		 */
		public boolean verifyMoveKing(ChessBoard board, ChessPiece[] arr, ChessPiece king, Pair newPos) 
			throws ArrayIndexOutOfBoundsException{
			
			//change in x and y coordinates 
			int deltaX = newPos.col() - king.getPos().col(); 
			int deltaY = newPos.row() - king.getPos().row(); 
			
			if(deltaY > 1 || deltaX > 1) {
				System.out.println("Illegal Move: King can only move one space at a time"); 
				return false; 
			}
			
			if(isEmpty(board, newPos.row(), newPos.col()) || isCapturable(board, king, newPos) ) {
				if(isChecked(board,arr, king.getPos())) {
					System.out.println("Illegal Move: Cannot move to square where king is attacked"); 
					return false; 
				}
				return true; 
			}
			
			
			return true; 
		}
		
		
		/**
		 * Checks if the king is in check
		 * @param board the chess board
		 * @param arr an array containing all the opponents pieces currently on the board
		 * @return boolean indicates whether the king is in check
		 */
		public boolean isChecked(ChessBoard board,ChessPiece[] arr,Pair kingPos) {
			
			//Redirect System.out to a ByteArrayOutputStream
			//this is to prevent error messaging from being logged
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        PrintStream printStream = new PrintStream(outputStream);
	        PrintStream originalOut = System.out;
	        System.setOut(printStream);
	        
	        
	        try {
	        	 for(int i = 0; i < arr.length; i++) {
	 	        	if(!arr[i].getIsCaptured()) {
	 	        		switch(arr[i].getRank()) {
	 	        		
	 	        		case PAWN:
	 	        			if(verifyMovePawn(board,arr[i],kingPos)) {
	 	        				return true; 
	 	        			}
	 	        			break;
	 	        		case KNIGHT:
	 	        			if(verifyMoveKnight(board,arr[i], kingPos)) {
	 	        				return true; 
	 	        			}
	 	        			break; 
	 	        		case BISHOP:
	 	        			if(verifyMoveBishop(board, arr[i], kingPos)) {
	 	        				return true; 
	 	        			} 
	 	        			break;
	 	        		case ROOK:
	 	        			if(verifyMoveRook(board, arr[i], kingPos)) {
	 	        				return true; 
	 	        			}
	 	        			break;
	 	        		case QUEEN:
	 	        			if(verifyMoveQueen(board, arr[i], kingPos)) {
	 	        				return true; 
	 	        			}
	 	        			break; 
	 	        		default:
	 	        			System.out.println("Error: Something went wrong");
	 	        			break; 
	 	        		
	 	        		}
	 	        		
	 	        	}
	 	        }
	        }
	        catch(ArrayIndexOutOfBoundsException ex) {
	        	ex.printStackTrace();
	        }
	        finally {
	        	 //restore original print stream
		        System.setOut(originalOut);
	        }
	        
	       
	        //indicates king is not in check
			return false; 
		}
		
		
		/**
		 * Checks whether a check mate has been achieved
		 * @param board the chess board
		 * @param arr an array of opponents active chess pieces 
		 * @param kingPos the position of the king on the board
		 * @return boolean indicates whether a check-mate has been achieved
		 */
		public boolean checkMate(ChessBoard board, ChessPiece[] arr, Pair kingPos) {
			
			//at any given positon, a king piece has maximum 8 possible moves
			Pair[] possibleMoves = new Pair[8];
			int counter = 0; 
			
			//we want to gather all possible and legal moves that a king in check can make
			for(int i = kingPos.row()+1; i < 3; i--) {
				for(int j = kingPos.col() +1; j < 3; j++) {
					try {
						if(isEmpty(board,i,j)) {
							possibleMoves[counter++] = new Pair(i,j); 
						}
					}
					catch(ArrayIndexOutOfBoundsException ex) { //for cases that go out of bounds, we ignore
						System.out.println("Exception: square is out of bounds"); 
						continue; 
					}
				}
				
			}
			//now we check whether moving the king to one of the possible squares will result in the king being safe
			for(int i = 0; i < counter; i++) {
				if(!isChecked(board,arr,possibleMoves[i])) {
					return false; 
				}
			}
			
			
			
			//this means that there is no possible move the king can make where it is not in check or check mate
			return true; 
		}
		
		
		/**
		 * Populates an array of chess pieces
		 * @param arr the array to be populated
		 * @param color the color of the chess piece
		 */
		public void generatePieces(ChessPiece[] arr, char color) {
			
			//add first row of pawns from left to right
			for(int i = 0; i < numPawns; i++) {
				arr[i] = new ChessPiece(PAWN,color);
			}
			
			int j = numPawns; 
			
			
			//add chess pieces in order from left to right
			arr[j++] = new ChessPiece(ROOK, color);
			arr[j++] = new ChessPiece(BISHOP, color); 
			arr[j++] = new ChessPiece(KNIGHT, color); 
			arr[j++] = new ChessPiece(QUEEN, color); 
			arr[j++] = new KingPiece(color); 
			arr[j++] = new ChessPiece(KNIGHT, color); 
			arr[j++] = new ChessPiece(BISHOP, color);
			arr[j++] = new ChessPiece(ROOK, color); 
			
			
		}
		
		
		/**
		 * Places all pieces onto the correct places at start of a chess match
		 * @param board the chess board
		 * @param white array of white chess pieces starting from the leftmost pawn (7A) and moving left to right
		 * @param black array of black chess pieces starting from the leftmost pawn (2A) and moving left to right
		 */
		public void setUpBoard(ChessBoard board, ChessPiece[] white, ChessPiece[] black) {
			
			//set up the white pieces
			int counter = 0; 
			for(int i = STARTWHITE; i >= 0; i-- ) {
				for(int j = A; j < COLUMNS; j++) {
					board.getBoard()[i][j] = white[counter]; 
					white[counter].setPos(i,j); 
					white[counter].setHasMoved(false);
					white[counter].setIsCaptured(false);
					counter++; 
				}
			}
			//set up the black pieces
			counter = 0; 
			for(int i = STARTBLACK; i < ROWS; i++ ) {
				for(int j = A; j < COLUMNS; j++) {
					board.getBoard()[i][j] = black[counter]; 
					black[counter].setPos(i,j); 
					black[counter].setHasMoved(false);
					black[counter].setIsCaptured(false);  
					counter++; 
				}
			}
			
			
		}
		
		
		
	
	}

	

	
	
		

