package uicomponents;

import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;

/**
 * Panel that displays the captured Chess Pieces.
 */
public class CapturedPiecesPanel extends FlowPane {
	
	/**
	 * List to store captured pieces.
	 */
	private List<ImageView> capturedPieces; 
	
	/**
	 * Constructor.
	 */
	public CapturedPiecesPanel() {
		
		this.capturedPieces = new ArrayList<ImageView>(); 
		
		
	}
	
	/**
	 * Adds captured piece to list and updates display. 
	 * @param capturedPiece the ImageView to add
	 */
	public void add(ImageView capturedPiece) {
		capturedPiece = this.shrink(capturedPiece);
		this.capturedPieces.add(capturedPiece);
		this.updateDisplay();
	}
	
	/**
	 * Resets internal list and display.
	 */
	public void reset() {
		this.capturedPieces.clear();
		this.getChildren().clear();
	}
	
	/**
	 * Adds formatting to component.
	 */
	public void format() {
		this.setHgap(4); // spacing between pieces
		this.setVgap(4); // spacing between rows
		this.setPrefWrapLength(100); // controls when it wraps to next row
		this.setPadding(new Insets(6));
		this.setAlignment(Pos.CENTER_LEFT);
	}; 
	
	/**
	 * Adds styling to component.
	 */
	public void style() {
		this.setStyle("""
			    -fx-background-color: #f5e0c5;  /* soft beige */
			    -fx-border-color: #d4bfa3;
			    -fx-border-width: 1;
			    -fx-border-radius: 4;
			    -fx-background-radius: 4;
			""");
	}; 
	
	/**
	 * Sets up the component.
	 */
	public void setup() {}; 
	
	/**
	 * Displays the captured pieces.
	 */
	public void display() {
		this.capturedPieces.forEach(view->{
			this.getChildren().add(view); 
		});
	}
	
	/**
	 * Updates display to display most recently captured piece.
	 */
	public void updateDisplay() {
		ImageView lastCapture = this.capturedPieces.getLast(); 
		this.getChildren().add(lastCapture); 
	}
	
	/**
	 * Shrinks ImageView.
	 * @param view the Image View to shrink
	 * @return the resized ImageView
	 */
	public ImageView shrink(ImageView view) {
		view.setFitWidth(20);
        view.setFitHeight(20);
        return view; 
	}
	
	
	
	
	
	

}
