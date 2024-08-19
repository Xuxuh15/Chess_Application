package ui;
import java.util.InputMismatchException;
import java.util.Scanner;

import helpers.Pair;
import logic.*; 

/**
 * Class representing a text console chess game
 * @author xuxuh
 */
public class ChessTextConsoleGame implements ChessConstants {
	
	
	/**
	 * Represents a check mate
	 */
	private boolean checkMate; 
	/**
	 * White chess pieces
	 */
	private ChessPiece[] piecesWhite = new ChessPiece[NUMPIECES]; 
	/**
	 * Black chess pieces
	 */
	private ChessPiece[] piecesBlack = new ChessPiece[NUMPIECES];
	/**
	 * the chess board
	 */
	private ChessBoard board = new ChessBoard(); 
	
	/**
	 * Represents whether current player has moved
	 */
	private boolean hasMoved = false; 
	/**
	 * the current player
	 */
	private char currentPlayer; 
	
	
	//maybe move error handling to the play game method instead of getuserInput
	
	/**
	 * Starts a new chess game
	 */
	public void playGame() {
		
		Scanner in = new Scanner(System.in);
		//game logic
		ChessLogic logic = new ChessLogic();
		
		//reset game parameters
		checkMate = false ;
		currentPlayer = WHITE; 
		board.resetBoard();
		
		//generate the chess pieces
		logic.generatePieces(piecesWhite, WHITE); //white pieces
		logic.generatePieces(piecesBlack, BLACK); //black pieces
		
		//set up the board
		logic.setUpBoard(board, piecesWhite, piecesBlack);
		
		//start game
		while(!checkMate) {
			
			hasMoved = false; 
			
			while(!hasMoved) {
				
				System.out.println("It is " + currentPlayer + "'s turn to play \n"); 
				//display the game board
				displayBoard(board); 
				
				try {
					System.out.println("\n Select a chess piece"); 
					//get player move
					Pair chessPieceSelection = getUserInput(in); 
					System.out.println("Pick a destination square"); 
					Pair playerMove = getUserInput(in); 
					//get the selected piece
					ChessPiece p = selectPiece(board, chessPieceSelection); 
					
					if(p.getColor() != currentPlayer) {
						System.out.println("Illegal Move: Cannot select opponent's pieces"); 
						break; 
					}
					
					//if the player is in check, make sure they move the king piece
					if(currentPlayer == WHITE) {
						if(logic.isChecked(board, piecesBlack, piecesWhite[INDEXKING].getPos())){
							if(p.getRank() != KING) {
								System.out.println("Illegal Selection: King is in check");
								break; 
							}
						}
					}
					else {
						if(logic.isChecked(board, piecesWhite, piecesBlack[INDEXKING].getPos())){
							if(p.getRank() != KING) {
								System.out.println("Illegal Selection: King is in check");
								break; 
							}
						}
					}
					
					
					//verify the move is legal and then move
					switch(p.getRank()) {
					
					case PAWN: 
						if(logic.verifyMovePawn(board, p, playerMove)) {
							logic.moveAndUpdate(board, p, playerMove);
							hasMoved = true; 
							
						}
						break; 
					case KNIGHT:
						if(logic.verifyMoveKnight(board, p, playerMove)) {
							logic.moveAndUpdate(board, p, playerMove);
							hasMoved = true; 
						}
						break; 
					case BISHOP:
						if(logic.verifyMoveBishop(board, p, playerMove)) {
							logic.moveAndUpdate(board, p, playerMove);
							hasMoved = true; 
						}
						break; 
					case ROOK:
						if(logic.verifyMoveRook(board, p, playerMove)) {
							logic.moveAndUpdate(board, p, playerMove);
							hasMoved = true; 
						}
						break;
					case QUEEN:
						if(logic.verifyMoveQueen(board, p, playerMove)) {
							logic.moveAndUpdate(board, p, playerMove);
							hasMoved = true; 
						}
						break;
					case KING:
						if(currentPlayer == WHITE) {
							if(logic.verifyMoveKing(board,piecesWhite, p, playerMove)) {
								logic.moveAndUpdate(board, p, playerMove);
								hasMoved = true;
							}
						}
						
						else {
							if(logic.verifyMoveKing(board,piecesBlack, p, playerMove)) {
								logic.moveAndUpdate(board, p, playerMove);
								hasMoved = true;
						 
							}
						
						}
						break; 
					}
					
					//check for check mate
					
					KingPiece king; 
					
					if(currentPlayer == WHITE) {
						king = (KingPiece)piecesBlack[INDEXKING]; 
						if(logic.isChecked(board, piecesWhite, king.getPos())){
							if(logic.checkMate(board, piecesWhite, king.getPos())) {
								System.out.println("Checkmate: White player wins"); 
								
								checkMate = true; 
							}
							//set king's checked parameter to true
							king.setIsChecked(true);
							
						}
					}
					else {
						king = (KingPiece)piecesWhite[INDEXKING]; 
						if(logic.isChecked(board, piecesBlack, king.getPos())){
							if(logic.checkMate(board, piecesBlack, king.getPos())) {
								System.out.println("Checkmate: Black player wins"); 
								
								checkMate = true; 
							}
							//set king's checked parameter to true
							king.setIsChecked(true);
						}
					}
					
					//toggle the current color
					if(!checkMate && hasMoved) {
						currentPlayer = currentPlayer == WHITE ? BLACK: WHITE; 
						hasMoved = false; 
					}
	
				}
				catch(ArrayIndexOutOfBoundsException ex) {
					System.out.println("Input a number [1-8] and letter [A-H]");  
				}
				catch(InputMismatchException ex) {
					System.out.println("Input must be in format of number:letter");
				}
				catch(NullPointerException ex) {
					System.out.println("Illegal Move: Cannot select an empty space"); 
				}
				catch(NumberFormatException ex) {
					System.out.println("Input must be in format of number:letter"); 
				}
				
			}
			
			
			
		}
		
		
		
	}
	
	/**
	 * Gets user input
	 * @return Pair  an object containing the specified row and column
	 * @throws InputMismatchException if the user inputs unexpected data or format
	 * @throws ArrayIndexOutOfBoundsException if the user inputs a square outside the bounds of the game board
	 */
	public Pair getUserInput(Scanner in) throws InputMismatchException, ArrayIndexOutOfBoundsException, NumberFormatException  {
		//create a new scanner object to read input
		
		try {
			
			int row; 
			int col; 
			System.out.println("Input a row and column (e.g. E:1) : "); 
			//get user input
			String str = in.next(); 
			//split strings into components
			row = Integer.parseInt(str.split(":")[1]) - 1; 
			col = convertLettertoNum(str.split(":")[0]);
			//return a pair object with the destination square
			return new Pair(row,col); 
		}
		catch(InputMismatchException e) { //wrong input format
			throw e; 
		}
		catch(ArrayIndexOutOfBoundsException e) { //specified value is out of bounds 
			throw e; 
		}
		catch(NumberFormatException e) {
			throw e; 
		}
		
		
	}
	
	
	/**
	 * Selects a piece on the chess board
	 * @param board the chess board
	 * @param square the specified square
	 * @return the chess piece selected
	 * @throws ArrayIndexOutOfBoundsException for specified square that is outside the board parameters
	 */
	public ChessPiece selectPiece(ChessBoard board, Pair square) 
		throws ArrayIndexOutOfBoundsException{
		
		return board.checkSpace(square.row(), square.col()); 
	}
	
	
	
	/**
	 * Used to convert user column input into corresponding integer
	 * @param l the column choice [A-H]
	 * @return the corresponding column value
	 */
	public int convertLettertoNum(String l) {
		
		switch(l.toUpperCase()) {
		
		case "A":
			return A; 
		case "B":
			return B; 
		case "C":
			return C; 
		case "D":
			return D; 
		case "E":
			return E; 
		case "F":
			return F; 
		case "G":
			return G; 
		case "H":
			return F; 
		default:
			return -1; 
			
		}
	}
	
	/**
	 * Displays the game board
	 * @param board the chess board
	 */
	public void displayBoard(ChessBoard board) {
		
		
		//print the column headers
		System.out.print("   "); 
		char header = 'A'; 
		for(int i = 0; i < ROWS; i++) {
			System.out.print(" " + header);
			header++; 
		}
		
		System.out.println(); 
		System.out.print( "   " + "_________________"); 
		
		//print the board 
		for(int i = 0; i < ROWS; i++) { 
			System.out.println(); 
			//row header
			System.out.print((i+1) + "  ");
			//board cells
			System.out.print("|");
			for(int j = 0; j < COLUMNS; j++) {
				
				ChessPiece currentPiece = board.getBoard()[i][j]; 
				
				try {
					if(currentPiece.getColor() == BLACK) {
						System.out.print("\u001B[32m" + currentPiece.getToken() + "\u001B[0m" + "|"); //print out token representation of chess piece
					}
					else {
						System.out.print(currentPiece.getToken() + "|"); //print out token representation of chess piece
					}
				}
				catch(NullPointerException ex) { //case where there is a null value
					System.out.print(" |"); 
				}
					
			}
			
		}
		System.out.println( "\n   " + "-----------------"); 
	}
	
	/**
	 * Main method
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		ChessTextConsoleGame chess = new ChessTextConsoleGame(); 
		
		chess.playGame(); 
		
		
	}

	
	
	
}
