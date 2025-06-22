package uicomponents;

import helpers.Pair;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.ChessConstants;

public class ChessTile extends StackPane  {
	
	/**
	 * The cooridnate of the tile on the board.
	 */
	private Pair coord; 
	/**
	 * The Chess Piece gui representation.
	 */
	private ImageView chessPieceImgView = null;
	/**
	 * The actual tile. 
	 */
	private Rectangle tile; 
	
	/**
	 * The color of the chess piece.
	 */
	private char chessPieceColor; 
	
	
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
		 this.chessPieceImgView = ChessPieceFactory.createPiece(chessPieceName);
		 this.chessPieceColor = chessPieceName.contains("white")? ChessConstants.WHITE : ChessConstants.BLACK; 

		chessPieceImgView.setPreserveRatio(true); // Preserve aspect ratio of the image
	    chessPieceImgView.setSmooth(true); // Smooth scaling of the image
	    // Bind ImageView's width and height to StackPane's width and height
	    //chessPiece.fitWidthProperty().bind(this.widthProperty());
	    //chessPiece.fitHeightProperty().bind(this.heightProperty());
	    chessPieceImgView.setId(chessPieceName); 
	    this.getChildren().add(chessPieceImgView);
		
	}
	
	/**
	 * Adds chess piece ImageView to the tile .
	 * @param imageView the ImageView to add 
	 */
	public void addChessPiece(ImageView imageView) {
		this.getChildren().add(imageView); 
		this.chessPieceImgView = imageView; 
		String name = imageView.getId(); 
		if(name.contains("white")) {
			this.chessPieceColor = ChessConstants.WHITE; 
		}
		else {
			this.chessPieceColor = ChessConstants.BLACK; 
		}
	}
	
	
	/**
	 * Removes the ChessPiece from this tile.
	 */
	public ImageView removeChessPiece() {
		this.getChildren().removeIf(node -> node instanceof ImageView); 
		ImageView chessPiece = this.chessPieceImgView; 
		this.chessPieceImgView = null; 
		this.chessPieceColor = 'n'; 
		return chessPiece; 
	}
	
	/**
	 * Returns the color of the Chess Piece on this tile. 
	 * @return a char or null if this tile does not contain a chess piece.
	 */
	public char getSelectedChessPieceColor() {
		return this.chessPieceColor; 
		
		
	}
	
	/**
	 * Checks whether the tile is empty.
	 * @return boolean. 
	 */
	public boolean isEmpty() {
		return this.chessPieceImgView == null; 
	}
	
	/**
	 * Returns the image on the tile.
	 * @return Image on the tile if exists or null.
	 */
	public ImageView getImageView() {
		return this.chessPieceImgView; 
	}
	
	
	
	
	
	

}
