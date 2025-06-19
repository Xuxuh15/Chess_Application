package ui;

import java.util.Iterator;

import helpers.Pair;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessLogic;
import logic.ChessPiece;
import uicomponents.GUIChessBoard;

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
	
	
	
	private GUIChessBoard board = new GUIChessBoard(); 
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		ObservableList<Node> tiles = board.getChildren(); 
		
		Iterator iter = tiles.iterator(); 
		
		while(iter.hasNext()) {
			ChessTile tile = (ChessTile) iter.next(); 
			tile.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
				//only update selected coords if it is the player's turn and they haven't moved yet.
				  if(canPlay) {
					boolean selectedCorrectColor = this.selectedCorrectColor(tile); 
					System.out.print("Selected correct color: " + selectedCorrectColor ); 
					  
					if(board.getSelectedSourceSquare() == null) {
						  
						  //player selected the wrong color piece
						  if(tile.isEmpty() || !selectedCorrectColor) {
							  return; 
						  }
						  else {
							  //if user has not selected a square yet, set the selected square to this coordinate
							  board.setSelectedSourceSquare(tile.getCoord()); 
							  System.out.println("Selected " + tile.getCoord()); 
						  }
							    
							  
					}
					else {
						 // if user selects the same square, diselect current square
						if(board.getSelectedSourceSquare() == tile.getCoord()) {
								board.setSelectedSourceSquare(null);   
								System.out.println("Diselected " + tile.getCoord()); 
							}
						//set the destination square and lock the move to be sent to the server
						else {
							this.canPlay = false; //lock the move in
							board.setSelectedDestinationSquare(tile.getCoord()); 
							System.out.println("Destination " + tile.getCoord());
						}
						
					}
			
				  }
			});
		}
		

		
		Scene scene = new Scene(this.board, 800, 800);
		
        primaryStage.setTitle("Chess UI");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        this.board.updateBoard(new Pair(1,1), new Pair(2,1));
		
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
	
	/**
	 * Resets a move.
	 */
	private void resetMove() {
		this.selectedSquare = null; 
		this.destinationSquare = null; 
	}
	
	/**
	 * Changes board state so player can select a move. 
	 */
	private void canPlay() {
		this.canPlay = true; 
	}
	
	
	
	
	
	//method to create label for game notifications
	
	//method for label for current player's turn
	
	//method for label for selected and destination square
	
	//a timer label on the top
	
	
	
	
	public static void main(String[] args) {
		
		launch(args); 
		
		
	}

}
