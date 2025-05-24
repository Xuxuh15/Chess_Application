package ui;

import helpers.Pair;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessLogic;
import logic.ChessPiece;

public class ChessUI extends Application {
	
	/**
	 * Represents a check mate
	 */
	private boolean checkmate; 
	/**
	 * White chess pieces
	 */
	private ChessPiece[] piecesWhite = ChessLogic.generatePieces(ChessConstants.WHITE);
	/*
	 * Black chess pieces
	 */
	private ChessPiece[] piecesBlack = ChessLogic.generatePieces(ChessConstants.BLACK);
	/**
	 * the chess board
	 */
	private ChessBoard board;  
	
	/**
	 * Represents whether current player has moved
	 */
	private boolean hasMoved = false; 
	
	private boolean isMyTurn = false; 
	/**
	 * the current player
	 */
	private char currentPlayer; 
	
	/**
	 * Game logic. 
	 */
	private ChessLogic logic; 
	
	private Pair selectedSquare; 
	private Pair destinationSquare; 
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		GridPane root = createBoard(); 

		
		Scene scene = new Scene(root, 800, 800);
		
        primaryStage.setTitle("Chess UI");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	/**
	 * Creates the UI Chess Board
	 * @return 
	 * @return a GridPane
	 */
	public GridPane createBoard() {
		GridPane root = new GridPane(); 
		root.setAlignment(Pos.CENTER);
	    // Make sure TilePane does not resize the number of columns (fixed grid)
	    root.setPrefWidth(600);
	    root.setPrefHeight(600);
	    root.setPadding(new Insets(30));
	    
		for(int i = 0; i < ChessConstants.ROWS; i++) {
	
			for(int j = 0; j < ChessConstants.COLUMNS; j++) {
	
				Color tileColor = ((i + j) % 2 == 0 ? Color.BEIGE : Color.BROWN);
				ChessTile tile = new ChessTile(new Pair(i,j), tileColor); 
				if(i == 0 || i == ChessConstants.ROWS -1) {
					tile.addChessPiece(ChessConstants.LAYOUT[j]);
				}
				else if(i == 1 || i == ChessConstants.ROWS - 2) {
					tile.addChessPiece("white_pawn");
				}
				
				tile.setPrefSize(70, 70);
				// Bind the width and height of the tile to the individual cell size based on the root size
				tile.autosize();
				tile.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
					
					  //only update selected coords if it is the player's turn and they haven't moved yet.
					  if(isMyTurn && !hasMoved) {
						  
						  ChessPiece selectedPiece = logic.peek(board, tile.getCoord()); 
						  
						  //nothing was selected
						  if(selectedPiece == null) {
							  return; 
						  }
						  boolean selectedCorrectColor = this.selectedCorrectColor(currentPlayer, selectedPiece);
						  
						  //player selected the wrong color piece
						  if(!selectedCorrectColor) {
							  return; 
						  }
						  else {
							  // if user selects the same square, diselect current square
							  if(this.selectedSquare == tile.getCoord()) {
								  this.selectedSquare = null; 
							  }
							  //if user has not selected a square yet, set the selected square to this coordinate
							  else if(this.selectedSquare == null) {
								  this.selectedSquare = tile.getCoord(); 
							  }
							  //user has selected a square with a valid piece and the newly selected square is a different coordinate. 
							  else {
								  this.destinationSquare = tile.getCoord(); 
							  }
							  //we can have a blocking wait while selected and destination square == null
						  }
						
					  }
					
					 
					 
					  
				});
				
				 
				
				root.add(tile, j, i); 
			}
		}
		return root; 
	}
	
	
		
	/**
	 * 
	 * @param currentPlayer the current player color
	 * @param selectedPiece the selected piece 
	 * @return true if player selected correct color
	 * @throws IllegalArgumentException if player selects opponents chess piece
	 */
	private boolean selectedCorrectColor(char currentPlayer, ChessPiece selectedPiece) throws IllegalArgumentException {
		//check to make sure player chooses correct color
		if(selectedPiece.getColor() != currentPlayer) {
			throw new IllegalArgumentException("Illegal Move: Cannot select opponent's pieces"); 
		}
		return true; 
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		launch(args); 
	}

}
