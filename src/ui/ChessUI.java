package ui;

import java.util.Iterator;

import javax.swing.GroupLayout.Alignment;

import helpers.Pair;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.ChessConstants;
import statemachine.SelectionController;
import statemachine.SelectionLockedState;
import uicomponents.BoardLabel;
import uicomponents.ChessTile;
import uicomponents.GUIChessBoard;
import uicomponents.PlayerInfoPanel;


/*
 * The UI interface for the Chess game.
 */
public class ChessUI extends Application {
	
	/**
	 * Handles state of player board selection. 
	 */
	private SelectionController controller = new SelectionController(); 
	
	/**
	 * The GUI ChessBoard.
	 */
	private GUIChessBoard board = new GUIChessBoard(); 
	
	private boolean ready = false; 
	boolean inSession = true; 
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		ObservableList<Node> tiles = board.getChildren(); 
		
		Iterator iter = tiles.iterator(); 
		
		//add event listener to the board tiles
		while(iter.hasNext()) {
			ChessTile tile = (ChessTile) iter.next(); 
			tile.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
			  if(controller.getCanPlay()) {
				  controller.onTileClicked(tile);
				  if (controller.getState() instanceof SelectionLockedState) {
	                  Pair source = controller.getSourceTile().getCoord();
	                  Pair destination = controller.getDestinationTile().getCoord();
	                  //send coord to server and wait for update
	                  board.updateBoard(source, destination); //only if server approves then update
	                  controller.resetSelection(); // go back to waiting for next selection
	              }
			  } 
			  
			}); 
				
		}
		

		
		Scene scene = new Scene(createMainGrid(), 1600, 800);
		//scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
        primaryStage.setTitle("Chess UI");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
      
		
	}
	
	/**
	 * Creates grid to hold chess board and chess labels. 
	 * @return GridPane
	 */
	private GridPane createMainGrid() {
		GridPane mainGrid = new GridPane(); 
		mainGrid.setAlignment(Pos.CENTER);
	    // Make sure TilePane does not resize the number of columns (fixed grid)
	    mainGrid.setPrefWidth(800);
	    mainGrid.setPrefHeight(800);
	    mainGrid.setPadding(new Insets(30));
	    mainGrid.add(new ColumnLabel(), 2, 0);
	    mainGrid.add(this.board, 2,1); 
	    mainGrid.add(new RowLabel(), 1, 1);
	    mainGrid.add(new PlayerInfoPanel("Player 1", "white_pawn", ChessConstants.YOUR_MOVE), 0,1); 
	    mainGrid.add(new PlayerInfoPanel("Player 2", "black_pawn", ChessConstants.WAIT), 3,1);
	    return mainGrid; 
	}
	
	/**
	 * The column labels for the chess board.
	 */
	public class ColumnLabel extends GridPane  {
		
		public ColumnLabel() {
			char label = 'A'; 
			for(int j = 0;  j < ChessConstants.COLUMNS; j++) {
				BoardLabel colLabel = new BoardLabel(label);
				colLabel.setPrefSize(BoardLabel.PREF_WIDTH, BoardLabel.PREF_HEIGHT - 50);
				this.add(colLabel,j,0); 
				this.setAlignment(Pos.CENTER); 
				label++; 
				
			}
			
		}
		
	}
	
	/**
	 * The row labels for the chess board.
	 */
	public class RowLabel extends GridPane  {
		
		public RowLabel() {
			
			int label = 1; 
			for(int i = 0;  i < ChessConstants.ROWS; i++) {
				BoardLabel rowLabel = new BoardLabel(String.valueOf(label)); 
				rowLabel.setPrefSize(BoardLabel.PREF_WIDTH - 50, BoardLabel.PREF_HEIGHT);
				this.add(rowLabel,0,i); 
				this.setAlignment(Pos.CENTER);
				label++; 
				
			}
			
		}
	}

	
	
	public static void main(String[] args) {
		
		launch(args); 
		
		
	}

}
