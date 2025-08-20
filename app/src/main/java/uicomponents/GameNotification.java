package uicomponents;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class GameNotification extends Label {

    public GameNotification(String message) {
        super(message);
        initialize();
    }

    private void initialize() {
        
        this.setStyle("-fx-background-color: rgba(0,0,0,0.7); " +
                      "-fx-text-fill: white; " +
                      "-fx-padding: 10px; " +
                      "-fx-font-size: 16px; " +
                      "-fx-border-radius: 5px; " +
                      "-fx-background-radius: 5px;");
        this.setOpacity(0); // initially invisible
    }

    /**
     * Displays this notification on the parent PlayerInfoPanel for the specified duration
     */
    public void show(PlayerInfoPanel parent, double durationSeconds) {
        if (!parent.getChildren().contains(this)) {
            parent.getChildren().add(this);
        }

        // Make it visible
        this.setOpacity(1);

        // Auto-hide after duration
        PauseTransition pause = new PauseTransition(Duration.seconds(durationSeconds));
        pause.setOnFinished(event -> parent.getChildren().remove(this));
        pause.play();
    }
}

