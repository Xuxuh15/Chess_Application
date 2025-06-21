package uicomponents;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Timer extends Label {
	
	private int duration = 30;
	private int currentTurnTimeRemaining; 
	private Timeline turnTimer;
	
	public Timer(int duration) {
		this.duration = duration; 
		this.currentTurnTimeRemaining = duration; 
		this.setText(formatTime(0)); 
		this.turnTimer =  new Timeline(new KeyFrame(Duration.seconds(1), e -> {
	        // Update label or timer logic here
	        this.setText(getUpdatedTime());
	    	})
		); 
		turnTimer.setCycleCount(Timeline.INDEFINITE);
		this.setStyle();
		this.format(); 
		turnTimer.play();
	}
	
	private String getUpdatedTime() {
		String time = "0:00:0"; 
		if (currentTurnTimeRemaining > 0) {
	        currentTurnTimeRemaining--;
	        time = formatTime(currentTurnTimeRemaining);
	    } 
		else{
			turnTimer.stop();
	    }
		return time; 
	}

	
	
	private String formatTime(int totalSeconds) {
	    int minutes = totalSeconds / 60;
	    int seconds = totalSeconds % 60;
	    return String.format("%02d:%02d", minutes, seconds);
	}
	
	public void startTurnTimer() {
	    currentTurnTimeRemaining = duration;
	    this.setText(formatTime(currentTurnTimeRemaining));
	    turnTimer.play();
	}

	public void stopTurnTimer() {
	    turnTimer.stop();
	}
	
	public void setDuration(int duration) {
		this.duration = duration; 
	}
	
	public void format() {
		this.setPrefWidth(200); // or some value that fits your longest time format
		this.setMinWidth(100);
		this.setMaxWidth(200);
		this.setAlignment(Pos.CENTER); // center text inside fixed width
	}
	
	public void setStyle() {
		this.setStyle(
			    "-fx-font-size: 26px; " +
			    "-fx-font-weight: bold; " +
			    "-fx-text-fill: #7f1d1d; " +          // deep dark red text
			    "-fx-background-color: #f5f0e6; " +   // light beige background
			    "-fx-padding: 12px 24px; " +
			    "-fx-background-radius: 12px; " +
			    "-fx-border-color: #a42e2e; " +       // medium red border
			    "-fx-border-width: 3px; " +
			    "-fx-border-radius: 12px; " +
			    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 4, 0, 0, 2);"
			);
	}
	
	
	
	

	

}
