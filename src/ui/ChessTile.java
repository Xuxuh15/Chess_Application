package ui;

import helpers.Pair;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uicomponents.ChessPieceFactory;

public class ChessTile extends StackPane  {
	
	
	private Pair coord; 
	private ImageView chessPiece; 
	private Rectangle tile; 
	
	
	public ChessTile(Pair coord, Color color) {
		
		this.setPrefSize(50, 50);
		
		this.coord = coord; 
		tile = new Rectangle();
		tile.widthProperty().bind(this.widthProperty());
		tile.heightProperty().bind(this.heightProperty());
		tile.setFill(color);
		this.getChildren().add(tile); 
		
		
		
		
	}
	
	
	public void setCoord(Pair newCoord) {
		this.coord = newCoord; 
	}
	
	public Pair getCoord() {
		return this.coord; 
	}
	
	public void addChessPiece(String chessPieceName) {
		ImageView chessPiece = ChessPieceFactory.createPiece(chessPieceName); 

		chessPiece.setPreserveRatio(true); // Preserve aspect ratio of the image
	    chessPiece.setSmooth(true); // Smooth scaling of the image
	    // Bind ImageView's width and height to StackPane's width and height
	    //chessPiece.fitWidthProperty().bind(this.widthProperty());
	    //chessPiece.fitHeightProperty().bind(this.heightProperty());
	    this.getChildren().add(chessPiece);
		
	}
	
	public void setImage(String imagePath) throws IllegalArgumentException {
		Image image = new Image(imagePath); 
		this.chessPiece.setImage(image);
	}
	
	public void setImage(Image image) {
		this.chessPiece.setImage(image);
	}
	
	public Image getImage() {
		return this.chessPiece.getImage(); 
	}
	
	
	
	
	
	

}
