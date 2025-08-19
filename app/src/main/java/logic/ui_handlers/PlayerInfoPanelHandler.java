package logic.ui_handlers;
import java.awt.Label;

import logic.ChessConstants;
import uicomponents.PlayerInfoPanel;
import uicomponents.StatusLabel;
import uicomponents.Timer;


public class PlayerInfoPanelHandler {
	
	
	private char playerColor; 
	
	private PlayerInfoPanel opponentPanel; 
	
	private PlayerInfoPanel myPanel; 
	
	/**
	 * Constructor. 
	 * @param myPanel player's ui panel.
	 * @param oppPanel the opponents ui panel.
	 * @param myColor player's color.
	 */
	public PlayerInfoPanelHandler(PlayerInfoPanel myPanel, PlayerInfoPanel oppPanel, char myColor) {
		this.myPanel = myPanel; 
		this.opponentPanel = oppPanel; 
		this.playerColor = myColor; 
		
	}
	
	
	/**
	 * Starts my turn timer.
	 */
	public void startMyTurnTimer() {
		Timer myTimer = myPanel.getTimer(); 
		myTimer.startTurnTimer(); 
		
	}
	/**
	 * Stops my turn timer. 
	 */
	public void stopMyTurnTimer() {
		Timer myTimer = myPanel.getTimer(); 
		myTimer.stopTurnTimer(); 
	}
	
	/**
	 * Resets my turn timer.
	 */
	public void resetMyTurnTimer() {
		Timer myTimer = myPanel.getTimer(); 
		myTimer.resetTimer();; 
	}
	
	/**
	 * Starts opponents turn timer. 
	 */
	public void startOpponentTurnTimer() {
		Timer oppTimer = myPanel.getTimer(); 
		oppTimer.startTurnTimer(); 
		
	}
	
	/**
	 * Stops opponents turn timer. 
	 */
	public void stopOpponentTurnTimer() {
		Timer oppTimer = myPanel.getTimer(); 
		oppTimer.stopTurnTimer(); 
	}
	
	/**
	 * Resets opponents turn timer.
	 */
	public void resetOpponentTurnTimer() {
		Timer oppTimer = myPanel.getTimer(); 
		oppTimer.resetTimer();; 
	}
	
	
	/**
	 * Set status label to the text provided.
	 * @param update text to update the label with.
	 */
	public void updateMyStatusLabel(String update) {
		StatusLabel myStatusLabel = myPanel.getStatusLabel(); 
		myStatusLabel.setText(update);
	}
	
	public void toggleTurn(char currentPlayer) {
		
		if(currentPlayer == this.playerColor) {
			this.myPanel.getStatusLabel().setStatus(ChessConstants.YOUR_MOVE);
			this.opponentPanel.getStatusLabel().setStatus(ChessConstants.WAIT);
		}
		else {
			this.myPanel.getStatusLabel().setStatus(ChessConstants.WAIT);
			this.opponentPanel.getStatusLabel().setStatus(ChessConstants.YOUR_MOVE);
		}
	}
	
	
	
	
	

}
