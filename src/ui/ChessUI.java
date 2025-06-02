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
	 * Represents whether current player can make a move. Click event will only fire when this is true.
	 */
	private boolean canPlay = true; 
	
	/**
	 * the current player
	 */
	private char currentPlayer = ChessConstants.WHITE; 
	
	/**
	 * The player's selected source square. 
	 */
	private Pair selectedSquare = null; 
	/**
	 * The player's selected destination square. 
	 */
	private Pair destinationSquare = null; 
	
	

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
					  if(canPlay) {
						boolean selectedCorrectColor = this.selectedCorrectColor(tile); 
						System.out.print("Selected correct color: " + selectedCorrectColor ); 
						  
						if(this.selectedSquare == null) {
							  
							  //player selected the wrong color piece
							  if(tile.isEmpty() || !selectedCorrectColor) {
								  return; 
							  }
							  else {
								  //if user has not selected a square yet, set the selected square to this coordinate
								  this.selectedSquare = tile.getCoord(); 
								  System.out.println("Selected " + tile.getCoord()); 
							  }
								    
								  
						}
						else {
							 // if user selects the same square, diselect current square
							if(this.selectedSquare == tile.getCoord()) {
									this.selectedSquare = null; 
									System.out.println("Diselected " + tile.getCoord()); 
								}
							//set the destination square and lock the move to be sent to the server
							else {
								this.canPlay = false; //lock the move in
								System.out.println("Destination " + tile.getCoord());
							}
							
						}
					
						
				}
					
					  
				});
				
				root.add(tile, j, i); //adds the tile to the board
			}
		}
		return root; 
	}
	
	
		
	
	 /**
	  * Checks if the player has selected the correct ChessPiece color. 
	  * @param tile the tile.
	  * @return a boolean.
	  */
	private boolean selectedCorrectColor(ChessTile tile) {
		char color = tile.getSelectedChessPieceColor(); 
		boolean selectedCorrectColor = false; 
		if(color == this.currentPlayer) {
			selectedCorrectColor = true; 
		}
		return selectedCorrectColor; 
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		launch(args); 
	}

}
