package logic;

import helpers.Pair;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
		return isValidMove; 
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
		 * Used to convert user column input into corresponding integer
		 * @param l the column choice [A-H]
		 * @return the corresponding column value
		 */
		public int convertLettertoNum(String l) {
			
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
	 	        		this.verifyMove(board, arr[i], kingPos); 
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
		public boolean checkmate(ChessBoard board, ChessPiece[] arr, Pair kingPos) {
			
			//at any given position, a king piece has maximum 8 possible moves
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
			//now we check whether there exists a destination square that results in no check
			for(int i = 0; i < counter; i++) {
				if(!isChecked(board,arr,possibleMoves[i])) {
					return false; 
				}
			}
			
			//if your opponent invokes this line, you're cooked  ¯\_(ツ)_/¯
			return true; 
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
		public ChessPiece[] generatePieces(char color) {
			
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

	

	
	
		

