package uicomponents;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class BoardLabel extends Label {
	
	
	public static int PREF_HEIGHT = 70; 
	public static int PREF_WIDTH = 70; 
	
	public BoardLabel(String label) {
		this.setText(label); 
		this.decorate();
	}
	
	public BoardLabel(char label) {
		this.setText(String.valueOf(label));
		this.decorate();
	}
	
	private void decorate() {
		this.setAlignment(Pos.CENTER);
		this.setPrefSize(BoardLabel.PREF_WIDTH, PREF_HEIGHT);
		this.autosize();
		this.setPadding(new Insets(5));
	}
	

	
	

}
