package uicomponents;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class that creates the image representation of chess pieces for the UI board.
 */
public class ChessPieceFactory {
	
	/**
	 * Sets the image view to the chess piece specifies in argument.
	 * @param pieceName the name of the .png file from the /resources/images/ folder
	 * @return a ImageView
	 */
    public static ImageView createPiece(String pieceName) {
        // Adjust the path to match your resources folder
        String imagePath = "/resources/images/" + pieceName + ".png";

        // Load the image
        Image image = new Image(ChessPieceFactory.class.getResourceAsStream(imagePath));

        // Create the ImageView and set image
        ImageView imageView = new ImageView(image);

        // Optional: resize the image
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setPreserveRatio(true);

        return imageView;
    }
}

