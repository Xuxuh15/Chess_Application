package logic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;

import helpers.Pair;

/**
 * Class that is responsible for handling the chess game logic
 * @author xuxuh
 */
public class ChessLogic implements ChessConstants {
	
	/**
	 * Strategy object used to verify a move is legal
	 */
	private VerificationStrategy verificationStrategy = null; 
	
	/**
	 * Array containing the white player's chess pieces
	 */
	private ChessPiece[] whitePlayerPieces = null; 
	/**
	 * Array containing the black player's chess pieces
	 */
	private ChessPiece[] blackPlayerPieces = null; 
	
	
	/**
	 * Verifies a move is legal
	 * @param board the chess board
	 * @param pieceToMove the chess piece to move
	 * @param newPos the destination square
	 * @return boolean whether the move is legal or not
	 */
	public boolean verifyMove(ChessBoard board, ChessPiece pieceToMove, Pair newPos) {
		
		boolean isValidMove = false; 
		
		if(pieceToMove != null && newPos != null && board != null) {
			switch(pieceToMove.getRank()) {
			
			case PAWN:
				this.verificationStrategy = new VerifyMovePawn(); 
				isValidMove = verificationStrategy.verifyMove(board, pieceToMove, newPos); 
				break; 
			case KNIGHT:
				this.verificationStrategy = new VerifyMoveKnight(); 
				isValidMove = verificationStrategy.verifyMove(board, pieceToMove, newPos);
				break; 
			case BISHOP:
				this.verificationStrategy = new VerifyMoveBishop(); 
				isValidMove = verificationStrategy.verifyMove(board, pieceToMove, newPos);
				break; 
			case ROOK:
				this.verificationStrategy = new VerifyMoveRook(); 
				isValidMove = verificationStrategy.verifyMove(board, pieceToMove, newPos);
				break; 
			case QUEEN:
				this.verificationStrategy = new VerifyMoveQueen(); 
				isValidMove = verificationStrategy.verifyMove(board, pieceToMove, newPos);
				break; 
			case KING:
				if(pieceToMove.getColor() == ChessConstants.WHITE) {
					this.verificationStrategy = new VerifyMoveKing(this.blackPlayerPieces); 
					
				}
				else {
					this.verificationStrategy = new VerifyMoveKing(this.whitePlayerPieces);
				}
				isValidMove = this.verificationStrategy.verifyMove(board, pieceToMove, newPos); 
				break; 
			default:
				System.out.println("Method<verifyMove> Error: Chess Piece not recognonized"); 
				break; 
			}
		
		}
		
		return isValidMove; 
	}
		
		/**
		 * A function that checks if the target square is empty
		 * @param board the chess board
		 * @param row the target row
		 * @param col the target column
		 * @return boolean indicates if target square is empty or not
		 * @throws ArrayIndexOutOfBoundsException a square out of bounds 
		 */
		public boolean isEmpty(ChessBoard board, int row, int col) throws ArrayIndexOutOfBoundsException {
			boolean isEmpty = board.checkSpace(row, col) == null;
			return isEmpty; 
			
		}
		
		/**
		 * Function that indicates whether piece in target square can be captured
		 * @param board the chess board
		 * @param color the ccolor of the chess piece attempting to capture
		 * @param dest the target square
		 * @return boolean indicates whether target square can be captured
		 * @throws ArrayIndexOutOfBoundsException a square out of bounds cannot be captured
		 */
		public boolean isCapturable(ChessBoard board, ChessPiece p, Pair dest) 
			throws ArrayIndexOutOfBoundsException{
			boolean isCapturable = !(board.checkSpace(dest.row(), dest.col()).getColor() == p.getColor()); 
			return isCapturable; 
		}
		
		
		/**
		 * Used to convert user column input into corresponding integer
		 * @param l the column choice [A-H]
		 * @return the corresponding column value
		 */
		public static int convertLettertoNum(String l) {
			
			int val = -1; 
			
			if(l != null) {
				switch(l.toUpperCase()) {
				
				case "A":
					val = A;
					break;
				case "B":
					val = B;
					break;
				case "C":
					val = C;
					break;
				case "D":
					val = D;
					break;
				case "E":
					val = E;
					break;
				case "F":
					val = F;
					break;
				case "G":
					val = G;
					break;
				case "H":
					val = H; 
					break;
				default:
					break;  
					
				}
			}
			return val; 
			
		}
		
		
		/**
		 * Checks if the king is in check
		 * @param board the chess board
		 * @param arr an array containing all the opponent's pieces currently on the board
		 * @param kingPos the current position of the King
		 * @return boolean indicates whether the king is in check
		 */
		public boolean isChecked(ChessBoard board,ChessPiece[] arr,Pair kingPos) {
			
			//Redirect System.out to a ByteArrayOutputStream
			//this is to prevent error messaging from being logged
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        PrintStream printStream = new PrintStream(outputStream);
	        PrintStream originalOut = System.out;
	        System.setOut(printStream);
	        
	        boolean inCheck = false; 
	        
	        //21-01-2025 IMPORTANT!! -- Refactor method to implement strategy patterns!
	        for(int i = 0; i < arr.length; i++) {
       		 ChessPiece p = arr[i]; 
 	        	if(!p.getIsCaptured() && p.getRank() != ChessConstants.KING) {
 	        		try {
 	        			if(this.verifyMove(board, arr[i], kingPos)) {
 	 	        			inCheck = true; 
 	 	        			break; 
 	 	        		} 
 	        		}
 	        		catch(IllegalArgumentException e) {
 	        			//do nothing
 	        		}
 	        	}
        	 }	
	        System.setOut(originalOut);
	        //indicates king is not in check
			return inCheck; 
		}
		
		
		/**
		 * Throws an exception if player tries to move a chess piece that is not the king while in check
		 * @param currentPlayer the current player color
		 * @param selectedPiece the currently selected piece
		 * @return false if not in check
		 * @throws IllegalArgumentException if the selected piece is not the king
		 */
		public boolean inCheck(ChessBoard board,ChessPiece selectedPiece, ChessPiece[] oppPieces, ChessPiece[] myPieces) throws IllegalArgumentException {
			
			if(this.isChecked(board, oppPieces, myPieces[ChessConstants.INDEXKING].getPos())){
				if(selectedPiece.getRank() != ChessConstants.KING) {
					throw new IllegalArgumentException("Illegal Selection: King is in check"); 
					 
				}
			}
			
			return false;
			
		}
		public ChessPiece[] getMyPieces(char currentPlayer) {
			if(currentPlayer == ChessConstants.WHITE) return this.whitePlayerPieces; 
			else return this.blackPlayerPieces; 
		}
		
		public ChessPiece[] getOpponentPieces(char currentPlayer) {
			if(currentPlayer == ChessConstants.WHITE) return this.blackPlayerPieces; 
			else return this.whitePlayerPieces; 
		}
		
		/**
		 * Checks whether checkmate condition has been achieved
		 * @param king the king chess piece being checked
		 * @return boolean whether checkmate condition has been achieved
		 */ 
		public boolean isCheckmate(ChessBoard board,char currentColor) {
			boolean checkmate = false; 
			KingPiece king = null; 
			ChessPiece[] opponentPieces = this.getOpponentPieces(currentColor); 
			ChessPiece[] myPieces = this.getMyPieces(currentColor); 
			
			king = (KingPiece)opponentPieces[ChessConstants.INDEXKING]; 
			if(this.isChecked(board, myPieces, king.getPos())){
				if(this.checkmate(board, myPieces, king)) {
					checkmate = true; 
					System.out.println("Checkmate!"); 
				}
				//set king's checked parameter to true
				king.setIsChecked(true);
				
			}
			
			return checkmate;
			
		}
		
		/**
		 * Returns a pair from its String representation (e.g. "row:col")
		 * @param str
		 * @return
		 */
		public static Pair getCoordinate(String str)  {

			try {
				int row; 
				int col; 
				//split strings into components
				row = Integer.parseInt(str.split(":")[1]) - 1; 
				col = ChessLogic.convertLettertoNum(str.split(":")[0]);
				//return a pair object with the destination square
				return new Pair(row,col); 
			}
			catch(InputMismatchException e) { //wrong input format
				throw e; 
			}
			catch(NumberFormatException e) {
				throw e; 
			}
		}
		
		/**
		 * Determines whether the current player selected their own piece.
		 * @param currentPlayer the current color
		 * @param selectedPiece the color of the selected piece
		 * @return true if the player color matches the selected piece
		 * @throws IllegalArgumentException if the color of the selected piece and the color of the player do
		 * not match
		 */
		public boolean selectedCorrectColor(char currentPlayer, ChessPiece selectedPiece) throws IllegalArgumentException {
			if(selectedPiece.getColor() != currentPlayer) {
				throw new IllegalArgumentException("Illegal Move: Player cannot select piece of opponent's color"); 
			} 
			return true; 
		}
		
		
		
		/**
		 * Checks whether a checkmate condition has been met.
		 * @param board the chess board
		 * @param arr an array of the opponent's active chess pieces 
		 * @param king the king in check
		 * @return boolean indicates whether a checkmate has been achieved
		 */
		public boolean checkmate(ChessBoard board, ChessPiece[] arr, KingPiece king) {
			
			//all possible (and valid) moves a king in check can make
			Pair[] possibleMoves = calculateSolutionSpace(board,king);
			boolean isCheckmate =  !this.kingCanMove(board, arr, possibleMoves);
			
			return isCheckmate; 
		}
		
		/**
		 * Checks whether a king piece can make a move that won't result in check. 
		 * @param board the chess board
		 * @param arr an array containing all the opponents pieces
		 * @param possibleMoves an array of Pair objects containing all the possible moves the king
		 * in check can make
		 * @return boolean value indicating whether the king can move or not
		 */
		private boolean kingCanMove(ChessBoard board, ChessPiece[] arr, Pair[] possibleMoves) {
			
			boolean kingCanMove = false; 
			//now we check whether there exists a destination square that results in no check
			for(int i = 0; i < possibleMoves.length; i++) {
				if( possibleMoves[i] != null && !isChecked(board,arr,possibleMoves[i])) {
					kingCanMove = true;
					break; 
				}
			}
			
			return kingCanMove; 
			
		}
		
		
		/**
		 * Returns a unit vector representing the direction along the y-axis a chess piece needs to move 
		 * to go forward.
		 * @param p a chess piece
		 * @return an int value [-1,1]
		 */
		private int getUnitVector(ChessPiece p) {
			int unit_vector = p.getColor() == ChessConstants.WHITE? ChessConstants.UNIT_VECTOR_WHITE : ChessConstants.UNIT_VECTOR_BLACK;
			
			return unit_vector; 
			
		}
		
		/**
		 * Returns solution space (all possible moves) for a king in check. 
		 * @param board the chess board
		 * @param king the king in check
		 * @return an array of coordinates
		 */
		private Pair[] calculateSolutionSpace(ChessBoard board, KingPiece king) {
			
			//at any given position, a king piece has maximum 8 possible moves
			Pair[] possibleMoves = new Pair[8];
			
			Pair kingPos = king.getPos(); 
			int counter = 0; 
			
			int unit_vector = this.getUnitVector(king); 
			
			//we want to gather all possible and legal moves that a king in check can make
			for(int i = kingPos.row() - unit_vector; i != kingPos.row() + (unit_vector*2) ; i+= unit_vector) {
				for(int j = kingPos.col() - 1; j <= kingPos.col() + 1; j++) {
					try {
						if(isEmpty(board,i,j) || this.isCapturable(board,king, new Pair(i,j))) {
							possibleMoves[counter++] = new Pair(i,j); 
						}
					}
					catch(ArrayIndexOutOfBoundsException ex) {
						System.out.println("Exception: square is out of bounds"); //cases out of bounds we ignore
					}
				}
				
			}
			
			return  possibleMoves; 
		}
		
		
		
		/**
		 * Moves a chess piece on the board and updates its position
		 * @param board the chess board
		 * @param newPos the new position
		 * @param pieceToMove the chess piece to be updated
		 */
		public void moveAndUpdate(ChessBoard board,ChessPiece pieceToMove, Pair newPos) {
			
			board.move(pieceToMove.getPos(), newPos);
			pieceToMove.setPos(newPos.row(), newPos.col());
			pieceToMove.willMove();
		}
		
		
		/**
		 * Populates an array of chess pieces
		 * @param arr the array to be populated
		 * @param color the color of the chess piece
		 */
		public static ChessPiece[] generatePieces(char color) {
			
			ChessPiece[] arrToReturn = new ChessPiece[ChessConstants.NUMPIECES]; 
			//add first row of pawns from left to right
			for(int i = 0; i < numPawns; i++) {
				arrToReturn[i] = new ChessPiece(PAWN,color);
			}
			
			int j = numPawns; 
			
			
			//add chess pieces in order from left to right
			arrToReturn[j++] = new ChessPiece(ROOK, color);
			arrToReturn[j++] = new ChessPiece(KNIGHT, color); 
			arrToReturn[j++] = new ChessPiece(BISHOP, color); 
			arrToReturn[j++] = new ChessPiece(QUEEN, color); 
			arrToReturn[j++] = new KingPiece(color); 
			arrToReturn[j++] = new ChessPiece(BISHOP, color); 
			arrToReturn[j++] = new ChessPiece(KNIGHT, color);
			arrToReturn[j++] = new ChessPiece(ROOK, color); 
			
			return arrToReturn; 	
			
		}
		
		
		/**
		 * Places all pieces onto the correct places at start of a chess match
		 * @param board the chess board
		 * @param white array of white chess pieces starting from the leftmost pawn (7A) and moving left to right
		 * @param black array of black chess pieces starting from the leftmost pawn (2A) and moving left to right
		 */
		public void setUpBoard(ChessBoard board) {
			
			if(this.blackPlayerPieces == null || this.whitePlayerPieces == null) {
				System.out.println("Method<setUpBoard> Error: chess piece arrays are empty. First generate pieces before setting up the board"); 
				return; 
			}
			
			//set up the white pieces
			int counter = 0; 
			for(int i = STARTWHITE; i >= 0; i-- ) {
				for(int j = A; j < COLUMNS; j++) {
					board.getBoard()[i][j] = this.whitePlayerPieces[counter]; 
					this.whitePlayerPieces[counter].setPos(i,j); 
					this.whitePlayerPieces[counter].setHasMoved(false);
					this.whitePlayerPieces[counter].setIsCaptured(false);
					counter++; 
				}
			}
			//set up the black pieces
			counter = 0; 
			for(int i = STARTBLACK; i < ROWS; i++ ) {
				for(int j = A; j < COLUMNS; j++) {
					board.getBoard()[i][j] = this.blackPlayerPieces[counter]; 
					blackPlayerPieces[counter].setPos(i,j); 
					blackPlayerPieces[counter].setHasMoved(false);
					blackPlayerPieces[counter].setIsCaptured(false);  
					counter++; 
				}
			}
			
			
		}
		
		/**
		 * Setter method for blackPlayerPieces
		 * @param arr populated array 
		 */
		public void setBlackPlayerChessArray(ChessPiece[] arr) {
			if(arr != null)this.blackPlayerPieces = arr; 
			
		}
		
		/**
		 * Setter method for whitePlayerPieces
		 * @param arr populated array
		 */
		public void setWhitePlayerChessArray(ChessPiece[] arr) {
			if(arr != null)this.whitePlayerPieces = arr;
			
		}
		
		/**
		 * Peeks at a chess coordinate. 
		 * @param square the square to peek at
		 * @return a Chess Piece if there is one on the specified square
		 * @throws ArrayIndexOutOfBoundsException for a coordinate outside the board
		 */
		public ChessPiece peek(ChessBoard board, Pair square) throws ArrayIndexOutOfBoundsException{
			return board.checkSpace(square.row(), square.col());
		}

	
	}

	

	
	
		

