package uicomponents;

import helpers.Pair;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import logic.ChessConstants;

/**
 * Class that represents the GUI Chess Board.
 */
public class GUIChessBoard extends GridPane {
	
	/**
	 * Currently selected 
	 */
	private Pair selectedSourceSquare; 
	
	private Pair selectedDestinationSquare; 
	
	
	
	/**
	 * Gets the selected source square.
	 * @return Pair 
	 */
	public Pair getSelectedSourceSquare() {
		return this.selectedSourceSquare; 
	}
	
	/**
	 * Gets the selected destination square.
	 * @return Pair
	 */
	public Pair getSelectedDestinationSquare() {
		return this.selectedDestinationSquare; 
	}
	
	/**
	 * Sets the new selected source square. 
	 * @param selected
	 */
	public void setSelectedSourceSquare(Pair selected) {
		this.selectedSourceSquare = selected; 
	}
	
	/**
	 * Sets new selected destination square. 
	 * @param destination
	 */
	public void setSelectedDestinationSquare(Pair destination ) {
		this.selectedDestinationSquare = destination; 
	}
	
	/**
	 * Returns the target chess tile if it exists. 
	 * @param target the square we want to return 
	 * @return the tile if it exists otherwise null
	 */
	public ChessTile peek(Pair target) {
		ChessTile tile = null; 
		if(target.row() < ChessConstants.ROWS && target.col() < ChessConstants.COLUMNS) {
			tile = (ChessTile) this.getChildren().get((target.row() * ChessConstants.ROWS) + target.col()); 
		}
		return tile; 
		
	}
	
	
	/**
	 * Will update board by moving gui chess piece to new location. 
	 * @param source the starting point
	 * @param destination the destination point
	 */
	public void updateBoard(Pair source, Pair destination) {
			
		ChessTile sourceTile = peek(source); 
			
		ChessTile destinationTile = peek(destination); 
			
		ImageView chessPieceView = sourceTile.getImageView(); 
		
		if(!destinationTile.isEmpty()) {
			destinationTile.removeChessPiece();
		}
		
		destinationTile.addChessPiece(chessPieceView);
			
		sourceTile.removeChessPiece();
			
			
		}
	
	
	
	/**
	 * Creates the UI Chess Board
	 * @return 
	 * @return a GridPane
	 */
	public GUIChessBoard() {
		 
		this.setAlignment(Pos.CENTER);
	    // Make sure TilePane does not resize the number of columns (fixed grid)
	    this.setPrefWidth(600);
	    this.setPrefHeight(600);
	    this.setPadding(new Insets(30));
	    
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
				//tile.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
					  
				//});
				
				
				
				this.add(tile, j, i); //adds the tile to the board
			}
		} 
	}
	

}
