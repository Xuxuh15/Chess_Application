package uicomponents;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Timer extends Label {
	
	private int duration = 30;
	private int currentTurnTimeRemaining; 
	private Timeline turnTimer;
	
	public Timer(int duration) {
		this.duration = duration; 
		this.setText(formatTime(0)); 
		this.turnTimer =  new Timeline(new KeyFrame(Duration.seconds(1), e -> {
	        // Update label or timer logic here
	        this.setText(getUpdatedTime());
	    	})
		); 
		turnTimer.setCycleCount(Timeline.INDEFINITE);
		//turnTimer.play();
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
	
	

	

}
