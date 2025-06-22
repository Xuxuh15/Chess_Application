package uicomponents;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import logic.ChessConstants;

public class StatusLabel extends Label {
	
	
	
	public StatusLabel(String text) {
		this.setText(text);
		this.setUp();
		//this.setAlignment(Pos.CENTER);
	}
	
	private void setUp() {
		this.style(); 
	}
	
	private void style() {
		this.setStyle("""
			    -fx-font-size: 18px;
			    -fx-text-fill: #7b1e1e;
			    -fx-background-color: transparent;
			    -fx-border-color: #d4bfa3;         /* Faint beige tone */
			    -fx-border-width: 0 0 1 0;
			    -fx-border-style: solid;
			    -fx-padding: 2 6 2 6;
			""");
	}
	
	public void setStatus(int status) {
	    String baseStyle = """
	        -fx-font-size: 18px;
	        -fx-background-color: transparent;
	        -fx-border-color: #d4bfa3;
	        -fx-border-width: 0 0 1 0;
	        -fx-border-style: solid;
	        -fx-padding: 2 6 2 6;
	    """;

	    switch (status) {
	        case ChessConstants.IN_CHECK:
	            this.setText("In Check");
	            this.setStyle(baseStyle + "-fx-text-fill: #a02020; -fx-font-weight: bold;");
	            break;
	        case ChessConstants.CHECKMATE:
	            this.setText("Checkmate");
	            this.setStyle(baseStyle + "-fx-text-fill: #800000; -fx-font-weight: bold;");
	            break;
	        case ChessConstants.YOUR_MOVE:
	            this.setText("Your Move");
	            this.setStyle(baseStyle + "-fx-text-fill: #228B22;");
	            break;
	        case ChessConstants.WAIT:
	            this.setText("Wait");
	            this.setStyle(baseStyle + "-fx-text-fill: #999;");
	            break;
	        default:
	            this.setText(""); // optional fallback
	            this.setStyle(baseStyle + "-fx-text-fill: #444;");
	            break;
	    }
	}
	
	

}
