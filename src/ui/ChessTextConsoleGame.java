package ui;
import java.util.InputMismatchException;
import java.util.Scanner;

import helpers.Pair;
import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessLogic;
import logic.ChessPiece;
import logic.KingPiece; 

/**
 * Class representing a text console chess game
 * @author xuxuh
 */
public class ChessTextConsoleGame implements ChessConstants {
	
	
	/**
	 * Represents a check mate
	 */
	private boolean checkmate; 
	/**
	 * White chess pieces
	 */
	private ChessPiece[] piecesWhite;
	/**
	 * Black chess pieces
	 */
	private ChessPiece[] piecesBlack;
	/**
	 * the chess board
	 */
	private ChessBoard board;  
	
	/**
	 * Represents whether current player has moved
	 */
	private boolean hasMoved = false; 
	/**
	 * the current player
	 */
	private char currentPlayer; 
	
	/**
	 * Chess logic.
	 */
	private ChessLogic logic; 
	
	/**
	 * Reference to the players' chess pieces.
	 */
	private ChessPiece[] myPieces; 
	
	/**
	 * Reference to opponents' chess pieces. 
	 */
	private ChessPiece[] opponentPieces;  
	
	
	//Getter methods -- primarily for testing purposes
	
	public boolean getHasMoved() {
		return this.hasMoved; 
	}
	
	public char getCurrentPlayer() {
		return currentPlayer; 
	}
	
	public boolean getCheckMate() {
		return this.checkmate; 
	}
	
	public ChessPiece[] getBlackChessPieces() {
		return this.piecesBlack; 
	}
	
	public ChessPiece[] getWhiteChessPieces() {
		return this.piecesWhite; 
	}
	
	
	
	
	/**
	 * Constructor. 
	 * @param board chess board object. 
	 */
	public ChessTextConsoleGame(ChessBoard board) {
		this.board = board; 
	}
	
	/**
	 * Starts a new chess game
	 */
	public void playGame() {
		
		Scanner in = new Scanner(System.in);
		
		this.setUpGame();
		
		//start game
		while(!checkmate) {
			
			hasMoved = false; 
			
			while(!hasMoved) {
				
				System.out.println("It is " + currentPlayer + "'s turn to play \n"); 
				//display the game board
				displayBoard(board); 
				
				try {
					
					ChessPiece selectedPiece = selectChessPiece(in); 
					Pair playerMove = getDestinationSquare(in); 
					
					
					//check to make sure player chooses correct color
					logic.selectedCorrectColor(currentPlayer, selectedPiece); 
					
					
					logic.inCheck(board, selectedPiece, this.myPieces, this.opponentPieces); //checks if player is in check
					
					//verify move is legal
					boolean isLegalMove = logic.verifyMove(board, selectedPiece, playerMove); 
					if(isLegalMove) {
						logic.moveAndUpdate(board, selectedPiece, playerMove);
						hasMoved = true;
					}
					else {
						throw new IllegalArgumentException("Invalid move"); 
					}
					
					//check for check mate
					this.checkmate = logic.isCheckmate(board, this.currentPlayer); 
					
					//toggle the current color
					if(!checkmate && hasMoved) {
						currentPlayer = currentPlayer == WHITE ? BLACK: WHITE;
						this.togglePlayers();
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
				catch(IllegalArgumentException ex) {
					//continue 
				}
			}
				
		}
			
	}
	
	/**
	 * Toggles the myPieces and opponentPieces attributes. 
	 */
	private void togglePlayers() {
		if(this.currentPlayer == ChessConstants.WHITE) {
			this.myPieces = this.piecesWhite; 
			this.opponentPieces = this.piecesBlack; 
		}
		else {
			this.myPieces = this.piecesBlack;
			this.opponentPieces = this.piecesWhite; 
		}
	}
		
		
	
	/**
	 * Returns the chess piece at a selected square.
	 * @param in scanner
	 * @return the currently selected chess piece
	 */
	public ChessPiece selectChessPiece(Scanner in) throws IllegalArgumentException {
		System.out.println("\n Select a chess piece"); 
		//get player move
		Pair chessPieceSelection = getUserInput(in); 
		//get the selected piece
		in.nextLine(); 
		ChessPiece selectedPiece = logic.peek(board, chessPieceSelection);
		if(selectedPiece == null) {
			throw new IllegalArgumentException("Illegal Move: You selected an empty squre"); 
		}
		return selectedPiece; 
		
		
	}
	
	/**
	 * Returns a Pair representing the destination square that a player wants to move their selected piece.s
	 * @param in scanner
	 * @return a pair representing the square the player wants to move their selected piece
	 */
	private Pair getDestinationSquare(Scanner in) {
		System.out.println("Pick a destination square"); 
		Pair playerMove = getUserInput(in);
		in.nextLine(); 
		return playerMove; 
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
			col = ChessLogic.convertLettertoNum(str.split(":")[0]);
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
	 * Sets up a new game.
	 */
	public void setUpGame() {
		//game logic
		this.logic = new ChessLogic();
		
		this.resetGame();
		
		this.generatePiecesAndSetUpBoard();
		
		
	}
	
	//maybe move this method to a separate class
	
	/**
	 * Generates the chess piece objects and sets up the board. 
	 */
	private void generatePiecesAndSetUpBoard() {
		//generate the chess pieces
		this.piecesWhite = ChessLogic.generatePieces(WHITE); //white pieces
		logic.setWhitePlayerChessArray(piecesWhite);
		this.myPieces = this.piecesWhite; 
		this.piecesBlack = ChessLogic.generatePieces(BLACK); //black pieces
		logic.setBlackPlayerChessArray(piecesBlack);
		this.opponentPieces = this.piecesBlack; 
		//set up the board
		logic.setUpBoard(board);
		
		
	}
	/**
	 * Reset the game parameters for a new game.
	 */
	private void resetGame() {
		//reset game parameters
		checkmate = false ;
		currentPlayer = WHITE; 
		board.resetBoard();
	}
	
	/**
	 * Main method
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		ChessTextConsoleGame chess = new ChessTextConsoleGame(new ChessBoard()); 
		
		chess.playGame(); 
		
		
	}

	
	
	
}
