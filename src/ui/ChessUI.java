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
import statemachine.SelectionController;
import uicomponents.GUIChessBoard;

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
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		ObservableList<Node> tiles = board.getChildren(); 
		
		Iterator iter = tiles.iterator(); 
		
		//add event listener to the board tiles
		while(iter.hasNext()) {
			ChessTile tile = (ChessTile) iter.next(); 
			tile.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{controller.onTileClicked(tile);}); 
				
		}
		

		
		Scene scene = new Scene(this.board, 800, 800);
		//scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
        primaryStage.setTitle("Chess UI");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        this.board.updateBoard(new Pair(1,1), new Pair(2,1));
		
	}

	
	
	public static void main(String[] args) {
		
		launch(args); 
		
		
	}

}
