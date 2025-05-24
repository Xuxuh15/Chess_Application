package ui;

import helpers.Pair;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
	/**
	 * the current player
	 */
	private char currentPlayer; 
	
	private ChessLogic logic; 
	
	

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
				 
				
				root.add(tile, j, i); 
			}
		}
		return root; 
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		launch(args); 
	}

}
