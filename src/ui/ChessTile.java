package ui;

import helpers.Pair;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uicomponents.ChessPieceFactory;

public class ChessTile extends StackPane  {
	
	/**
	 * The cooridnate of the tile on the board.
	 */
	private Pair coord; 
	/**
	 * The Chess Piece gui representation.
	 */
	private ImageView chessPiece = null;
	/**
	 * The actual tile. 
	 */
	private Rectangle tile; 
	
	
	/**
	 * Constructor 
	 * @param coord the coordinate of the tile on the Chess Board 
	 * @param color the tile color
	 */
	public ChessTile(Pair coord, Color color) {
		
		this.setPrefSize(50, 50);
		
		this.coord = coord; 
		tile = new Rectangle();
		tile.widthProperty().bind(this.widthProperty());
		tile.heightProperty().bind(this.heightProperty());
		tile.setFill(color);
		this.getChildren().add(tile); 
			
		
	}
	
	/**
	 * Setter method for coordinate.
	 * @param newCoord the new coordinate to set. 
	 */
	public void setCoord(Pair newCoord) {
		this.coord = newCoord; 
	}
	
	/**
	 * Getter method for the coordinate.
	 * @return the tile's coordinate.
	 */
	public Pair getCoord() {
		return this.coord; 
	}
	
	/**
	 * Adds/Modifies the chess piece image on the tile.
	 * @param chessPieceName the name of the .png file to add.
	 * @see resources/images/
	 */
	public void addChessPiece(String chessPieceName) {
		ImageView chessPiece = ChessPieceFactory.createPiece(chessPieceName); 

		chessPiece.setPreserveRatio(true); // Preserve aspect ratio of the image
	    chessPiece.setSmooth(true); // Smooth scaling of the image
	    // Bind ImageView's width and height to StackPane's width and height
	    //chessPiece.fitWidthProperty().bind(this.widthProperty());
	    //chessPiece.fitHeightProperty().bind(this.heightProperty());
	    this.getChildren().add(chessPiece);
		
	}
	
	/**
	 * Returns the image on the tile.
	 * @return Image on the tile if exists or null.
	 */
	public Image getImage() {
		return this.chessPiece.getImage(); 
	}
	
	
	
	
	
	

}
