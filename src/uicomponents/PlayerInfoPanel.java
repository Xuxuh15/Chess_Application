package uicomponents;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import logic.ChessConstants;

/**
 * Panel to hold player icon, name, timer and status label.
 */
public class PlayerInfoPanel extends VBox {
	
	
	private Timer stopclock; 
	
	private Label playerName; 
	
	private StatusLabel statusLabel;
	
	private ImageView icon; 
	
	
	public PlayerInfoPanel(String playerName, String icon, int status) {
		this.playerName = this.createPlayerNameLabel(playerName); 
		this.stopclock = new Timer(30); 
		this.statusLabel = new StatusLabel("Your Move"); 
		statusLabel.setStatus(status);
		this.icon = ChessPieceFactory.createPiece(icon); 
		
		this.setUp(); 
		
	}
	
	private  void format() {
		this.setPrefSize(300, 300);
		this.setSpacing(10);
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
		 	
	}
	
	private void setUp() {
		
		this.format();
		
		 // VBox to hold the icon, timer, and player name
        VBox infoBox = new VBox(5);
        infoBox.setAlignment(Pos.TOP_CENTER);
        infoBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        // Add icon, timer, and name to infoBox
        infoBox.getChildren().addAll(this.icon,this.stopclock, this.playerName);
        

        VBox statusBox = new VBox(statusLabel);
        statusBox.setAlignment(Pos.CENTER); // Center vertically
        statusBox.setPrefWidth(100); // Optional: control layout width
        
        // Add both boxes to the PlayerInfoPanel (HBox)
        this.getChildren().addAll(infoBox, statusBox);
	}
	
	
	public Timer getTimer() {
		return this.stopclock; 
	}
	
	public Label getPlayerName() {
		return this.playerName; 
	}
	
	public StatusLabel getStatusLabel() {
		return this.statusLabel; 
	}
	
	public ImageView getIcon() {
		return this.icon; 
	}
	
	public void setPlayerName(String playerName) {
		this.playerName.setText(playerName); 
	}
	
	public void setStatus(String status) {
		this.statusLabel.setText(status);
	}
	
	public void setIcon(String iconName) {
		this.icon = ChessPieceFactory.createPiece(iconName); 
	}
	
	
	private Label createPlayerNameLabel(String playerName) {
		Label playerNameLabel = new Label(playerName); 
		playerNameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
		return playerNameLabel; 
	}
	
	
	
	
	
	
	
	
	
	

}
